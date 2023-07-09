CREATE TABLE `person` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    birthDate DATE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE `guardian` (
    email VARCHAR(100) UNIQUE NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    personId BIGINT UNIQUE, 
    FOREIGN KEY(personId) REFERENCES `person`(id) ON DELETE CASCADE,
    PRIMARY KEY(personId)
);

CREATE TABLE `family_group` (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL
);

CREATE TABLE `guardian_in_family_group` (
    familyGroupId BIGINT NOT NULL, 
    guardianId BIGINT NOT NULL, 

    FOREIGN KEY(familyGroupId) REFERENCES `family_group`(id) ON DELETE CASCADE,
    FOREIGN KEY(guardianId) REFERENCES `guardian`(personId) ON DELETE CASCADE,
    PRIMARY KEY(familyGroupId, guardianId)
);

CREATE TABLE `dependent` (
    personId BIGINT UNIQUE,

    familyGroupId BIGINT,
    FOREIGN KEY (familyGroupId) REFERENCES `family_group`(id),

    FOREIGN KEY(personId) REFERENCES `person`(id) ON DELETE CASCADE,
    PRIMARY KEY(personId)
);

CREATE TABLE `group_activity` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id)
);

CREATE TABLE `guard`(
    dependentId BIGINT NOT NULL, 
    guardianId BIGINT NOT NULL, 

    daysOfWeek VARCHAR(56),
    guardianRole VARCHAR(9) NOT NULL,

    FOREIGN KEY(dependentId) REFERENCES `dependent`(personId) ON DELETE CASCADE,
    FOREIGN KEY(guardianId) REFERENCES `guardian`(personId) ON DELETE CASCADE,
    PRIMARY KEY(dependentId, guardianId)
);

CREATE TABLE `activity`(
    id BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(500),
    dateStart DATE NOT NULL,
    dateEnd DATE NOT NULL,
    hourStart TIME NOT NULL,
    hourEnd TIME NOT NULL,
    `state` VARCHAR(50) NOT NULL,
    commentary VARCHAR(500),
    
    dependentId BIGINT NOT NULL,
    currentGuardianId BIGINT NOT NULL, 
    actorId BIGINT NOT NULL, 
    createdBy BIGINT NOT NULL,
    finishedBy BIGINT,   
    groupActivityId BIGINT,   
      
    FOREIGN KEY(dependentId) REFERENCES `dependent`(personId),
    FOREIGN KEY(currentGuardianId) REFERENCES `guardian`(personId),
    FOREIGN KEY(actorId) REFERENCES `person`(id),
    FOREIGN KEY(createdBy) REFERENCES `guardian`(personId),
    FOREIGN KEY(finishedBy) REFERENCES `guardian`(personId),
    FOREIGN KEY(groupActivityId) REFERENCES `group_activity`(id),
    PRIMARY KEY(id)
);

CREATE TABLE `spent`(
    id BIGINT NOT NULL AUTO_INCREMENT,
    `value` INT NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    paidOn DATE NOT NULL,

    dependentId BIGINT NOT NULL, 
    guardianId BIGINT NOT NULL, 
    activityId BIGINT, 

    FOREIGN KEY(dependentId) REFERENCES `dependent`(personId) ON DELETE CASCADE,
    FOREIGN KEY(guardianId) REFERENCES `guardian`(personId) ON DELETE CASCADE,
    FOREIGN KEY(activityId) REFERENCES `activity`(id)
);