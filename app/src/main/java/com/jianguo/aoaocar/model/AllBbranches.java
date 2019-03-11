package com.jianguo.aoaocar.model;

import java.util.List;

/**
 * Created by 22077 on 2017/11/23.
 */

public class AllBbranches {

    public int id;
    public String district;
    public List<Branches>  mBranchess;
    public boolean  isSelect=false;
    public  AllBbranches(int id,String district,List<Branches>  mBranchess,boolean  isSelect){
        this.id=id;
        this.district=district;
        this.mBranchess=mBranchess;
        this.isSelect=isSelect;
    }

}
