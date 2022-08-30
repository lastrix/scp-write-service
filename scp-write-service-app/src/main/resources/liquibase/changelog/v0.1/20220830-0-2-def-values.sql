INSERT INTO scp_write_service.session(session_id)
VALUES (1);
INSERT INTO scp_write_service.spec(spec_id)
VALUES ('00000000-0000-0000-0000-000000000000');
INSERT INTO scp_write_service.spec(spec_id)
VALUES ('00000000-0000-0000-0000-000000000001');
INSERT INTO scp_write_service.enrollee(user_id, session_id)
VALUES ('00000000-0000-0000-0000-000000000000', 1);
