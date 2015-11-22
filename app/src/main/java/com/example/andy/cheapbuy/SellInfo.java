

package com.example.andy.cheapbuy;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by andy on 11/18/15.
*/
@ParseClassName("SellInfo")
public class SellInfo extends ParseObject {



    public String getUsername(){
        return getString("username");
    }

    public void setUsername(String username){
        put("username",username);
    }

    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String description){
        put("description",description);
    }

    public int getPrice(){
        return getInt("price");
    }

    public void setPrice(int price){
        put("price",price);
    }

    public ParseFile getPhoto1(){
        return getParseFile("photo1");
    }

    public void setPhoto1(ParseFile photo1){
        put("photo1",photo1);
    }

    public ParseFile getPhoto2(){
        return getParseFile("photo2");
    }

    public void setPhoto2(ParseFile photo2){
        put("photo2",photo2);
    }

    public ParseFile getPhoto3(){
        return getParseFile("photo3");
    }

    public void setPhoto3(ParseFile photo3){
        put("photo3",photo3);
    }

}
