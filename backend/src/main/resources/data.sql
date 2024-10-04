INSERT INTO public.role (id, name)
VALUES (1, 'ROLE_ADMIN'),(2, 'ROLE_USER');

INSERT INTO public.member (id, email, name, password, member_id)
VALUES (0, 'admin-solar-watch@admin.com', 'admin', '$2a$10$PwZF9bw.xLxGCwJpep/tc.sENEAVT8c.pctGKRtVggJwNLE3hTQn.','dfc5780a-8bd2-434d-93a6-7b4124276b6a');

INSERT INTO public.members_roles (role_id, member_id)
VALUES (1, 0);