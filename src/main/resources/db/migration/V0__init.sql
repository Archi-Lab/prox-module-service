
CREATE TABLE IF NOT EXISTS public.hops_module_mapping (
                                          id uuid NOT NULL,
                                          kuerzel character varying(255),
                                          version character varying(255),
                                          module_id uuid
);

CREATE TABLE IF NOT EXISTS public.hops_module_mapping (
                                                        id uuid NOT NULL,
                                                        kuerzel character varying(255),
                                                        version character varying(255),
                                                        module_id uuid
);


CREATE TABLE IF NOT EXISTS public.hops_study_course_mapping (
                                                id uuid NOT NULL,
                                                "study_course_kürzel" character varying(255),
                                                study_course_id uuid
);


CREATE TABLE IF NOT EXISTS public.module (
                             id uuid NOT NULL,
                             description character varying(9000),
                             name character varying(255),
                             module_id uuid
);

CREATE TABLE IF NOT EXISTS public.study_course (
                                   id uuid NOT NULL,
                                   academic_degree integer,
                                   name character varying(255),
                                   parent_study_course_id uuid
);


CREATE TABLE IF NOT EXISTS public.study_course_modules (
                                           study_course_id uuid NOT NULL,
                                           modules_id uuid NOT NULL
);
