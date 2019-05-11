package com.peruapps.tareas.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName="Tarea")
public class Tarea implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id_tarea")
    @SerializedName("id_tarea")
    private Integer id_tarea;


    @SerializedName("Nombre")
    private String Nombre;
    @SerializedName("Detalles")
    private String Detalles;


    @SerializedName("Fecha")
    private String Fecha;

    @SerializedName("Terminada")
    private Boolean Terminada;



    public Integer getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(Integer id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String detalles) {
        Detalles = detalles;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Boolean getTerminada() {
        return Terminada;
    }

    public void setTerminada(Boolean terminada) {
        Terminada = terminada;
    }



    public Tarea(String Nombre, String Detalles, String Fecha, Boolean Terminada)
    {
       // this.id_almacen=id_almacen;
      //  this.id_tarea=id_tarea;
        this.Nombre=Nombre;
        this.Detalles = Detalles;
        this.Fecha=Fecha;
        this.Terminada = Terminada;

    }



    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id_tarea);
        dest.writeString(this.Nombre);
        dest.writeString(this.Detalles);
        dest.writeString(this.Fecha);
        dest.writeByte((byte) (Terminada ? 1 : 0));


    }


    public Tarea(Parcel in) {
        id_tarea = in.readInt();
        Nombre = in.readString();
        Detalles = in.readString();
        Fecha = in.readString();
        Terminada=in.readByte() != 0;

    }


    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };


}
