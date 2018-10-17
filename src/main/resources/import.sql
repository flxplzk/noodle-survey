insert into user
  (
  account_non_expired,
  account_non_locked,
  credentials_non_expired,
  enabled,
  first_name,
  last_name,
  password,
  username,
  id) values
  (default,
  default,
  default,
  default,
  'Admin',
  'Admin',
  '$2a$10$ecU.4CgTUVSZKGzOJ5Ekm.bv6yOKLManEmz7sBV6i/qaU0Z8zycb2',
  'admin@admin.de',
  hibernate_sequence.nextval);
