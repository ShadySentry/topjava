DROP TABLE IF EXISTS meals;
CREATE TABLE meals
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    description VARCHAR                           NOT NULL,
    dateTime    TIMESTAMP           DEFAULT now() NOT NULL,
    calories    INTEGER             DEFAULT 2000  NOT NULL,
    user_id     INTEGER             DEFAULT 2000  NOT NULL
);

-- CREATE INDEX users_idx
--   ON public.meals(user_id);