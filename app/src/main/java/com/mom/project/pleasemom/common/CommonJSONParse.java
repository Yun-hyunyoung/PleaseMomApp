package com.mom.project.pleasemom.common;

import com.mom.project.pleasemom.dto.MemberDTO;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by 08_718 on 2016-10-28.
 */
public class CommonJSONParse {
    public static MemberDTO json2MemberDTO(JSONObject obj){
        MemberDTO dto = new MemberDTO();
        try {
            JSONStringer stringer = new JSONStringer();

            dto.setMem_num(Integer.parseInt(obj.getString("mem_num")));
            dto.setMem_id(obj.getString("mem_id"));
            dto.setMem_name(obj.getString("mem_name"));
            dto.setMem_phone(obj.getString("mem_phone"));
            dto.setMem_birth(obj.getString("mem_birth"));
            dto.setMem_case(obj.getString("mem_case"));
            dto.setMem_picture(obj.getString("mem_picture"));
            dto.setMem_email_ck(obj.getString("mem_email_ck"));
            dto.setMem_phone_ck(obj.getString("mem_phone_ck"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dto;
    }
}
