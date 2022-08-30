-- we must hold identifiers that exist in our realm
-- each time organizations require enrollees to apply - new session should be created
-- probably each year
CREATE TABLE scp_write_service.session
(
    session_id INT     NOT NULL,
    active     BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT session_pk PRIMARY KEY (session_id)
);

-- this table should contain all identifiers of all specs created withing system for integrity reasons
-- only spec from education organization should be used
CREATE TABLE scp_write_service.spec
(
    spec_id UUID    NOT NULL,
    active  BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT spec_pk PRIMARY KEY (spec_id)
);


CREATE TABLE scp_write_service.enrollee
(
    user_id          UUID      NOT NULL,
    session_id       INT       NOT NULL,
    -- if this user should no longer be able to perform any operation
    disabled         BOOLEAN   NOT NULL DEFAULT FALSE,
    -- selected speciality id
    selected_spec_id UUID               DEFAULT NULL,
    -- how much application may be sent by this enrollee
    selection_count  SMALLINT  NOT NULL DEFAULT 20,
    created_stamp    TIMESTAMP NOT NULL DEFAULT current_timestamp,
    modified_stamp   TIMESTAMP NOT NULL DEFAULT current_timestamp,
    CONSTRAINT enrollee_pk PRIMARY KEY (user_id, session_id),
    CONSTRAINT enrollee_session_id_fk FOREIGN KEY (session_id) REFERENCES scp_write_service.session (session_id),
    CONSTRAINT enrollee_selected_spec_id_fk FOREIGN KEY (selected_spec_id) REFERENCES scp_write_service.spec (spec_id),
    CONSTRAINT enrollee_selection_count_positive_chk CHECK ( selection_count >= 0 )
);

CREATE TABLE scp_write_service.enrollee_select
(
    user_id         UUID      NOT NULL,
    session_id      INT       NOT NULL,
    spec_id         UUID      NOT NULL,
    -- current selection status, 0 for applied, 1 for confirmed and 2 for cancelled
    status          SMALLINT  NOT NULL DEFAULT 0,
    -- the assigned user score
    score           INT       NOT NULL DEFAULT 0,
    -- stamp for creation(application) date
    created_stamp   TIMESTAMP NOT NULL DEFAULT current_timestamp,
    -- stamp for confirmation date
    confirmed_stamp TIMESTAMP          DEFAULT NULL,
    -- stamp for cancelled date
    canceled_stamp  TIMESTAMP          DEFAULT NULL,

    ---------------------------------------
    --     integrity check fields        --
    ---------------------------------------
    -- each time modification occurs, this stamp should be updated
    modified_stamp  TIMESTAMP NOT NULL DEFAULT current_timestamp,
    -- should be treated like 'version', this way we will make
    -- possible to reduce amount of response messages sent by target module
    -- otherwise we'll deal with lots of duplicates
    ordinal         SMALLINT           DEFAULT 0,
    -- current integrity check state, 0 - not sent, 1 - sent but not confirmed, 2 - sent and confirmed
    state           SMALLINT  NOT NULL DEFAULT 0,

    CONSTRAINT enrollee_select_pk PRIMARY KEY (user_id, session_id, spec_id),
    CONSTRAINT enrollee_select_user_fk FOREIGN KEY (user_id, session_id) REFERENCES scp_write_service.enrollee (user_id, session_id),
    CONSTRAINT enrollee_select_spec_fk FOREIGN KEY (spec_id) REFERENCES scp_write_service.spec (spec_id)
);
CREATE INDEX enrollee_select_modified_idx ON scp_write_service.enrollee_select (modified_stamp ASC);
CREATE INDEX enrollee_select_status_idx ON scp_write_service.enrollee_select (state);
