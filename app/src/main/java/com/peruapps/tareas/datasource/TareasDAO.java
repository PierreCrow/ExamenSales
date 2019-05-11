package com.peruapps.tareas.datasource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.peruapps.tareas.entities.Tarea;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TareasDAO {

    @Insert
    void InsertOnlyOne(Tarea tarea);
    @Insert
    void InsertMultiple(ArrayList<Tarea> tareas);

    @Update
    void Update(Tarea tarea);

    @Delete
    void Delete(Tarea tarea);

    @Query("DELETE  FROM Tarea")
    void DeleteAll();

    @Query ("SELECT * FROM Tarea WHERE id_tarea = :almacenID")
    Tarea Select_by_ID(int almacenID);

    @Query("SELECT * FROM Tarea")
    List<Tarea> GetAll();

    @Query( "DELETE from Tarea where id_tarea=:idTarea")
    void DeleteById(int idTarea);

    @Query("UPDATE Tarea set Nombre=:nombre, Detalles=:detalle, Fecha=:fecha, Terminada=:terminada WHERE id_tarea=:idTarea")
    void UpdateTarea(int idTarea, String nombre,String detalle,String fecha,Boolean terminada);



}
