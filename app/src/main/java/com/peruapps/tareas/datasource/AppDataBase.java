package com.peruapps.tareas.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.peruapps.tareas.entities.Tarea;


@Database(entities = { Tarea.class},
        version =2 , exportSchema = false)


public abstract class AppDataBase extends RoomDatabase {




    public abstract TareasDAO tareasDAO();


}
