package com.example.nagya.bestinpest.network.GameNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tickets {

@SerializedName("SUBWAY")
@Expose
private Integer sUBWAY;
@SerializedName("BUS-TROLLEY")
@Expose
private Integer bUSTROLLEY;
@SerializedName("TRAM")
@Expose
private Integer tRAM;

public Integer getSUBWAY() {
return sUBWAY;
}

public void setSUBWAY(Integer sUBWAY) {
this.sUBWAY = sUBWAY;
}

public Integer getBUSTROLLEY() {
return bUSTROLLEY;
}

public void setBUSTROLLEY(Integer bUSTROLLEY) {
this.bUSTROLLEY = bUSTROLLEY;
}

public Integer getTRAM() {
return tRAM;
}

public void setTRAM(Integer tRAM) {
this.tRAM = tRAM;
}

}