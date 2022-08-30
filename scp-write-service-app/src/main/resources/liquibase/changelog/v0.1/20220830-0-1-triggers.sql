-- this function works as trigger, ensuring valid state for our entries
-- when user tries to insert data
CREATE FUNCTION scp_write_service.select_insert()
    RETURNS trigger AS
$select_insert$
BEGIN
    -- do not allow invalid parameters to be passed
    -- confirmation or cancelling must be performed by UPDATE statement
    -- rather than insert
    IF NEW.status <> 0 THEN
        RAISE EXCEPTION 'Invalid status value : %', NEW.status
            USING HINT = 'status = 0';
    END IF;
    UPDATE scp_write_service.enrollee e
    SET selection_count = selection_count - 1,
        modified_stamp  = current_timestamp
    WHERE e.user_id = NEW.user_id
      AND e.session_id = NEW.session_id;
    -- we do not modify data here
    RETURN NEW;
END;
$select_insert$ LANGUAGE plpgsql;

CREATE TRIGGER select_insert_trg
    BEFORE INSERT
    ON scp_write_service.enrollee_select
    FOR EACH ROW
EXECUTE FUNCTION scp_write_service.select_insert();

-------
CREATE FUNCTION scp_write_service.select_update()
    RETURNS trigger AS
$select_update$
DECLARE
    previous UUID;
BEGIN
    IF OLD.status > NEW.status THEN
        RAISE EXCEPTION 'Invalid status: %', NEW.status
            USING HINT = 'status should be greater than previous value: ' || OLD.status;
    ELSEIF OLD.status <> NEW.status THEN
        IF NEW.status = 1 THEN
            NEW.confirmed_stamp = current_timestamp;

            SELECT selected_spec_id
            INTO previous
            FROM scp_write_service.enrollee e
            WHERE e.user_id = NEW.user_id
              AND e.session_id = NEW.session_id;

            IF previous IS NOT NULL THEN
                UPDATE scp_write_service.enrollee_select e
                SET canceled_stamp = current_timestamp,
                    modified_stamp = current_timestamp,
                    status         = 2,
                    ordinal        = ordinal + 1,
                    state          = 0
                WHERE e.user_id = NEW.user_id
                  AND e.session_id = NEW.session_id
                  AND e.spec_id = previous;
            END IF;
            UPDATE scp_write_service.enrollee e
            SET selected_spec_id = NEW.spec_id,
                modified_stamp   = current_timestamp
            WHERE e.user_id = NEW.user_id
              AND e.session_id = NEW.session_id;
        ELSEIF NEW.status = 2 THEN
            NEW.canceled_stamp = current_timestamp;
            UPDATE scp_write_service.enrollee e
            SET selected_spec_id = NULL,
                modified_stamp   = current_timestamp
            WHERE e.user_id = NEW.user_id
              AND e.session_id = NEW.session_id
              AND e.selected_spec_id = NEW.spec_id;
        ELSE
            -- prevent any status value different from 1 or 2
            RAISE EXCEPTION 'Invalid status: %', NEW.status
                USING HINT = 'status must be 1 or 2';
        END IF;
    END IF;
    IF OLD.status <> NEW.status OR OLD.score <> NEW.score THEN
        NEW.ordinal = NEW.ordinal + 1;
        NEW.state = 0;
        NEW.modified_stamp = current_timestamp;
    END IF;
    -- otherwise changes performed by DB directly
    -- and we should not affect those changes
    -- (timeout for confirmation reached and state dropped to 0)
    RETURN NEW;
END;
$select_update$ LANGUAGE plpgsql;

CREATE TRIGGER select_update_trg
    BEFORE UPDATE
    ON scp_write_service.enrollee_select
    FOR EACH ROW
EXECUTE FUNCTION scp_write_service.select_update();
