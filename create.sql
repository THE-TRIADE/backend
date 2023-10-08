create sequence activity_SEQ start with 1 increment by 50;
create sequence "family-group_SEQ" start with 1 increment by 50;
create sequence "group-activity_SEQ" start with 1 increment by 50;
create sequence person_SEQ start with 1 increment by 50;
create sequence spent_SEQ start with 1 increment by 50;

    create table activity (
       id bigint not null,
        commentary varchar(255),
        dateEnd date not null,
        dateStart date not null,
        description varchar(255),
        hourEnd time not null,
        hourStart time not null,
        name varchar(255) not null,
        state varchar(255) not null,
        actorId bigint,
        createdById bigint,
        currentGuardianId bigint,
        dependentId bigint,
        finishedById bigint,
        groupActivityId bigint,
        primary key (id)
    );

    create table dependent (
       id bigint not null,
        familyGroupId bigint not null,
        primary key (id)
    );

    create table "family-group" (
       id bigint not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table "group-activity" (
       id bigint not null,
        primary key (id)
    );

    create table guard (
       daysOfWeek smallint array,
        guardianRole smallint not null,
        guardianId bigint not null,
        dependentId bigint not null,
        primary key (dependentId, guardianId)
    );

    create table guardian (
       email varchar(255) not null,
        password varchar(255) not null,
        id bigint not null,
        primary key (id)
    );

    create table guardianInFamilyGroup (
       familyGroupId bigint not null,
        guardianId bigint not null,
        primary key (familyGroupId, guardianId)
    );

    create table person (
       id bigint not null,
        birthDate date not null,
        cpf varchar(255) not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table spent (
       id bigint not null,
        amount integer not null,
        name varchar(255) not null,
        paidOn date not null,
        activityId bigint,
        dependentId bigint,
        guardianId bigint,
        primary key (id)
    );

    alter table if exists guardian 
       add constraint UK_hfhgsqan9yctoj9n4g7j7s0i0 unique (email);

    alter table if exists activity 
       add constraint FKrkwtmmo7tjif3eutwtkkpgi7x 
       foreign key (actorId) 
       references person;

    alter table if exists activity 
       add constraint FK2s0iu78l92bi4j2mx9sgf8c3o 
       foreign key (createdById) 
       references guardian;

    alter table if exists activity 
       add constraint FKrr0r6hodtbq642m2eahv01sne 
       foreign key (currentGuardianId) 
       references guardian;

    alter table if exists activity 
       add constraint FK8mkgi9fg0fm2l0m1hhc1orq0e 
       foreign key (dependentId) 
       references dependent;

    alter table if exists activity 
       add constraint FK64ggoybyd2twqwgh9df7qua3r 
       foreign key (finishedById) 
       references guardian;

    alter table if exists activity 
       add constraint FKrsbs37c0emadxb86v9uu5gcgd 
       foreign key (groupActivityId) 
       references "group-activity";

    alter table if exists dependent 
       add constraint FKq0w8o1b1jxritjpd2q7jdl3wv 
       foreign key (familyGroupId) 
       references "family-group" 
       on delete cascade;

    alter table if exists dependent 
       add constraint FKs40iaxtw67i9ldljlxye1rm31 
       foreign key (id) 
       references person;

    alter table if exists guard 
       add constraint FKnhne25hx8uv7ghqgmbkdv1ful 
       foreign key (guardianId) 
       references guardian;

    alter table if exists guard 
       add constraint FKt12fvprjyeransijtlddjm262 
       foreign key (dependentId) 
       references dependent;

    alter table if exists guardian 
       add constraint FKhxljc59j6q3jbmnbh9vbdru47 
       foreign key (id) 
       references person;

    alter table if exists guardianInFamilyGroup 
       add constraint FKr9ppx6nq5y7mi23d7gu60xly8 
       foreign key (guardianId) 
       references guardian;

    alter table if exists guardianInFamilyGroup 
       add constraint FKa1uu2sgwrg6tcdpp8yypbo7lf 
       foreign key (familyGroupId) 
       references "family-group";

    alter table if exists spent 
       add constraint FKd0t8qw8katuw8ljyb3hgwj6dn 
       foreign key (activityId) 
       references activity;

    alter table if exists spent 
       add constraint FK6pirg1etswvs4ckqpsri8326y 
       foreign key (dependentId) 
       references dependent;

    alter table if exists spent 
       add constraint FK6k960pd14jbm2b6uu1unr5jph 
       foreign key (guardianId) 
       references guardian;
