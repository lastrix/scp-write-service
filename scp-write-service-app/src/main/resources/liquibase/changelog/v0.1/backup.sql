-- we must hold identifiers that exist in our realm
CREATE TABLE scp_write_service.selection_session
(
    selection_session_id INT     NOT NULL,
    active               BOOLEAN NOT NULL,
    CONSTRAINT selection_session_pk PRIMARY KEY (selection_session_id)
);

-- this table should contain all identifiers of all specs created withing system for integrity reasons
CREATE TABLE scp_write_service.spec
(
    spec_id UUID    NOT NULL,
    active  BOOLEAN NOT NULL,
    CONSTRAINT spec_pk PRIMARY KEY (spec_id)
)

CREATE TABLE scp_write_service.enrollee
(
    user_id              UUID    NOT NULL,
    selection_session_id INT     NOT NULL,
    disabled             BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT enrollee_pk PRIMARY KEY (user_id, selection_session_id),
    CONSTRAINT enrollee_selection_session_id_fk FOREIGN KEY (selection_session_id) REFERENCES scp_write_service.selection_session (selection_session_id)
);

CREATE TABLE scp_write_service.enrollee_event
(
    user_id              UUID     NOT NULL,
    selection_session_id INT      NOT NULL,
    spec_id              UUID     NOT NULL,
    -- event type may have following values
    -- - 1 - the user has applied to spec
    -- - 2 - the user received rating
    -- - 3 - the user approved his application, thus making spec desired
    -- - 4 - the user cancelled this application and no longed capable to reapply
    event_type           SMALLINT NOT NULL DEFAULT 0,
    -- creation date of this stamp
    created_stamp        TIMESTAMP         DEFAULT current_timestamp NOT NULL,
    -- when this event was sent to message queue
    sent_stamp           TIMESTAMP         DEFAULT current_timestamp NOT NULL,
    ordinal              SMALLINT          DEFAULT 0,
    -- when event is sent to message queue, this field should be set to true,
    -- if confirmation is not received within X, this flag is reset to false
    -- and ordinal increased by 1, which should force message sending
    sent                 BOOLEAN  NOT NULL DEFAULT FALSE,
    -- this flag hold information that this message was acknowledged by recipient
    confirmed            BOOLEAN  NOT NULL DEFAULT FALSE,
    -- may hold json or any other information that may help in further processing
    payload              TEXT              DEFAULT NULL,
    -- we make primary key composite here because each event may occur only once
    -- after that adding same event type to same person within same spec should be
    -- considered as error
    CONSTRAINT enrollee_event_pk PRIMARY KEY (user_id, selection_session_id, spec_id, event_type),
    CONSTRAINT enrollee_event_user_fk FOREIGN KEY (user_id, selection_session_id) REFERENCES scp_write_service.enrollee (user_id, selection_session_id),
    CONSTRAINT enrollee_event_spec_fk FOREIGN KEY (spec_id) REFERENCES scp_write_service.spec (spec_id)
);
