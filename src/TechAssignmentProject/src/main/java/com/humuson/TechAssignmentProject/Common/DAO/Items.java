package com.humuson.TechAssignmentProject.Common.DAO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    private String itemid;
    private String itemname;
    private String itemprice;
    private String itemdescription;
    private String itemimage;
    private String itemcategory;
    private String itemsubcategory;
}
