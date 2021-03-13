CREATE TABLE IF NOT EXISTS ANIMALS
(
    ID      INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    SPECIES varchar(256)                          UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS VETS
(
    ID      INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    NAME    varchar(256)                                 NOT NULL,
    SURNAME varchar(256)                                 NOT NULL
);

CREATE TABLE IF NOT EXISTS VISITS
(
    ID             INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    VET_ID         int                                          NOT NULL,
    ANIMAL_ID      int                                          NOT NULL,
    START_TIME     time                                         NOT NULL,
    END_TIME       time                                         NOT NULL,
    START_DATE     date                                         NOT NULL,
    END_DATE       date                                         NOT NULL,
    PRICE          money                                        NOT NULL,
    VISIT_TYPE     int                                          NOT NULL,
    OPERATION_TYPE int,

    CONSTRAINT fk_vet
        FOREIGN KEY (VET_ID)
            REFERENCES VETS (ID),

    CONSTRAINT fk_animal
        FOREIGN KEY (ANIMAL_ID)
            REFERENCES ANIMALS (ID)
);
