package com.app.showboxd.common.dto;

import lombok.Data;

@Data
public class CreatedBy{
    public int id;
    public String credit_id;
    public String name;
    public String original_name;
    public int gender;
    public String profile_path;
}