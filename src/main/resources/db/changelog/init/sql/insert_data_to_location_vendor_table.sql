INSERT INTO location_vendor (loc_id, vn_id, lv_created)
SELECT v.loc_id, v.vn_id, v.vn_created
FROM vendor v;