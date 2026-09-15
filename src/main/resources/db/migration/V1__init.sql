CREATE TABLE channels (
    channel_id    VARCHAR(100) PRIMARY KEY,
    display_name  VARCHAR(200) NOT NULL,
    created_at    TIMESTAMP NOT NULL
);

CREATE TABLE verdict_records (
    message_id    UUID PRIMARY KEY,
    channel_id    VARCHAR(100) NOT NULL REFERENCES channels (channel_id),
    user_id       VARCHAR(100) NOT NULL,
    spam_score    DOUBLE PRECISION NOT NULL,
    action        VARCHAR(20) NOT NULL,
    created_at    TIMESTAMP NOT NULL
);

CREATE INDEX idx_verdict_records_channel_created
    ON verdict_records (channel_id, created_at DESC);

CREATE TABLE verdict_record_reasons (
    message_id    UUID NOT NULL REFERENCES verdict_records (message_id),
    reason        VARCHAR(200) NOT NULL
);

CREATE INDEX idx_verdict_record_reasons_message
    ON verdict_record_reasons (message_id);
