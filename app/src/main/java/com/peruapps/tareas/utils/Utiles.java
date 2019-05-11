package com.peruapps.tareas.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.peruapps.tareas.datasource.AppDataBase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utiles {


    private Context ctx;
    public Utiles(Context ctx){
        this.ctx = ctx;
    }


    public static String NAMESPACE = "http://facturapp.org/";
    public static String URLL = "http://xxx.xxx.xx.xxx:xxxx/VisualImpact/Service1.asmx?WSDL";

    public static String SOAP_ACTION_VERSIONAPP = "http://namespace.org/VersionApp";
    public static String METHOD_NAME_VERSIONAPP = "VersionApp";

    public static String URL_FIJO="http://www.futuremobile.com.pe";
    public static String URL = URL_FIJO+"/WebService_FacturApp/ServicioWeb.asmx";


    public static String SOAP_ACTION_CREATE_USER = "http://facturapp.org/CreateUsuarioEmpresa";
    public static String SOAP_ACTION_LOGIN= "http://facturapp.org/Login";
    public static String SOAP_ACTION_Verifica_Empresa_Activada= "http://facturapp.org/Verifica_Empresa_Activada";


    public static String METHOD_NAME_CREATE_USER = "CreateUsuarioEmpresa";
    public static String METHOD_NAME_LOGIN = "Login";
    public static String METHOD_NAME_Verifica_Empresa_Activada = "Verifica_Empresa_Activada";



    //---------------------------------------------------------------------------------------

    public static String AppVersion="1.0.1";
    public static String NOMBRE_MI_EMPRESA="SENDA";
    public static String RUC_MI_EMPRESA="208747393738";
    public static String CORREO_MI_EMPRESA="senda@gmail.com";

    //----------------------------------------------------------------------------------------



    public static DecimalFormat df = new DecimalFormat("#0.00");


    public  static String IGV="18";

    public  static String NUMERO_SERIE_FACTURA="F001-000";
    public  static String NUMERO_SERIE_BOLETA="B001-000";
    public  static String NUMERO_SERIE_NOTADEVENTA="NV001-000";
    public  static String NUMERO_SERIE_COTIZACION="C001-000";

    public static String APPNAME="FacturApp";
    public static String PATH_INTERNAL_MEWMORY="";
    public static String PATH_EXTERNAL_MEWMORY="/sdcard/FuturoMovil/"+APPNAME+"/";

    public static final String DATABASE_NAME = "FACTURAPP_db";

    public static final String TABLA_VENDEDORES = "Vendedores";
    public static final String TABLA_CLIENTES = "Clientes";
    public static final String TABLA_ITEMS = "Items";
    public static final String TABLA_CONFIGURACION = "Configuracion";
    public static final String TABLA_EMPRESA = "Empresa";
    public static final String TABLA_COMPROBANTES = "Comprobantes";
    public static final String TABLA_DETALLECOMPROBANTES = "DetalleComprobantes";
    public static final String TABLA_ALMACENES = "Almacenes";
    public static final String TABLA_VENTAS = "Ventas";
    public static final String TABLA_USUARIO = "Usuarios";

    public static final String TABLA_CATEGORIA = "CategoriaProductos";
    public static final String TABLA_PROVEEDOR = "Proveedores";
    public static final String TABLA_ENTRADASTOCK = "Gasto";





    public static AppDataBase Inicia_BBDD_LOCAL(Context ctx)
    {
        AppDataBase APPDATABASE;

        APPDATABASE = Room.databaseBuilder(ctx,
                AppDataBase.class, Utiles.DATABASE_NAME).fallbackToDestructiveMigration().build();

        return APPDATABASE;
    }

    //carga imagen  a imageView
    public static void loadImageFromStorage(String Path_Foto,ImageView iv)
    {
        try {
            File f=new File(Path_Foto);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            // ImageView img=(ImageView)findViewById(R.id.imgPicker);
            iv.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    //devuelve la ruta de la imagen guardada
    public static String saveImageToStorage(Context ctx,Bitmap bitmapImage,String nombrefoto,String Tabla,String LugarAlmacenamiento){
       // ContextWrapper cw = new ContextWrapper(ctx);
        // path to /data/data/yourapp/app_data/imageDir



/*

        String root = Environment.getExternalStorageDirectory().toString();
        File myDire = new File(root + "/FACTURAPP");
        File myDir = new File(myDire + "/Imagenes");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

*/












        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

        Date currentTime = Calendar.getInstance().getTime();
        String Fecha=currentTime.toString().replace(" ","");

        File Dir1=null;

        if(LugarAlmacenamiento.equals("TARJETA SD")) {


            if(isSDPresent==true)
            {
                // yes SD-card is present
            //    Dir1 = new File(Utiles.PATH_EXTERNAL_MEWMORY + "Images/" + Tabla + "/");//cw.getDir("senda", Context.MODE_PRIVATE);
             //   if (!Dir1.exists()) {
              //      Dir1.mkdir();
              //  }


               // File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                String root = Environment.getExternalStorageDirectory().toString();
                File myDire = new File(root + "/FACTURAPP");
                File path = new File(myDire + "/Imagenes");


                // Create imageDir
                File mypath=new File(path,nombrefoto.replace(" ","")+".jpg");

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return mypath.getAbsolutePath().toString();
            }
            else
            {
                // Sorry
                Toast.makeText(ctx, "No tiene Tarjeda SD en el dispositivo", Toast.LENGTH_SHORT).show();

                return "";
            }


        }
        else
        {
             Dir1= ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES );
            if (!Dir1.exists()) {
                Dir1.mkdir();
            }
            // Create imageDir
            File mypath=new File(Dir1,nombrefoto.replace(" ","")+".jpg");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return mypath.getAbsolutePath();

        }


    }


    //guarda archivo txt de una tabla
    public static void saveTableIntxt(Context ctx,String DataJson,String Tabla,String LugarAlmacenamiento) {

        File Dir1;

        Date currentTime = Calendar.getInstance().getTime();
        String Fecha=currentTime.toString().replace(" ","");

        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();


        if(isSDPresent==true)
        {
          //  File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            File pathi =Environment.getExternalStorageDirectory();

            String root = Environment.getExternalStorageDirectory().toString();
            File myDire = new File(root + "/FACTURAPP");
            File path = new File(myDire + "/Documentos");

          //  File path = ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

            if (!path.exists()) {
                path.mkdir();
            }

            File file = new File(path, Tabla+".txt");
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);
                outputStreamWriter.write(DataJson);
                outputStreamWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            // Sorry
            Toast.makeText(ctx, "No tiene Tarjeda SD en el dispositivo", Toast.LENGTH_SHORT).show();

        }



    }


    //borra cualquier archivo dandole la ruta
    private void DeleteFile(String Path)
    {
        File file = new File(Path);
        boolean deleted = file.delete();
    }


    //convierte arraylist a formato json
    public static String ArrayListToJson(ArrayList<?> lista)
    {
        JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(lista);
        String JSONTOTAL=jsonElements.toString();

        return  JSONTOTAL;
    }









    // devuelve JSON en string de una tabla
    public static String readFromStringFile(Context ctx,String Tabla,String LugarAlmacenamiento) {

        String ret = "";

        File directory;
        File Dir1=null;

        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();


        if(LugarAlmacenamiento.equals("TARJETA SD")) {


            if(isSDPresent==true) {
                Dir1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            }

              //  Dir1 = new File(Utiles.PATH_EXTERNAL_MEWMORY + "Data/" + Tabla + "/");//cw.getDir("senda", Context.MODE_PRIVATE);
        }
        else
        {
           // File Dir1= ctx.getExternalCacheDir();
           // directory=new File(Dir1,Utiles.APPNAME+ "Data/" + Tabla + "/");

            Dir1= ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
           // directory=new File(Dir1,Utiles.APPNAME);

            // directory=ctx.getExternalCacheDir();
        }

        try {
           // File folder = ctx.getExternalCacheDir();
          //  File file = new File(folder, "system32.ini");
            File file = new File(Dir1, Tabla+".txt");
           // File file = new File(directory, Tabla+".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = br.readLine()) != null) {
                stringBuilder.append(receiveString);
            }

            ret = stringBuilder.toString();

          //  Log.e("COMEDORES", ret);

        } catch (FileNotFoundException e) {
          //  Log.e("COMEDORES", "File not found: " + e.toString());
        } catch (IOException e) {
          //  Log.e("COMEDORES", "Can not read file: " + e.toString());
        }

        return ret;


    }



    //funcion que carga una url de una foto a un imageview (utiliza GLIDE)
    public static void UrlToImageView(String urlFoto, ImageView imagev, Context contexto) {

        Glide.with(contexto)
                .load(urlFoto)
                //.override(100, 200)
                .fitCenter()
                .into(imagev);
    }


    //funcion que devuelve true si esta conectado a internet sino devuelve false
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean conectado=null;
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting())
            conectado= true;
        else {
            conectado= false;
        }
        return  conectado;
    }



    //VERIFICA SI UN SERVICIO EN EJCUCION
    public static boolean isMyServiceRunning(Class<?> serviceClass,Context ctx) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }








    //-------------------------------------------------------------------------------------
    //SPINERS

    public static void SeteaSpinner_DEPARTAMENTOS(Spinner spiner,Context ctx)
    {
        final List<String> departments = new ArrayList<>(Arrays.asList(Departamento_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,departments){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }


    public static void SeteaSpinner_TIPO_DOCUMENTO(Spinner spiner,Context ctx)
    {
        final List<String> tipos = new ArrayList<>(Arrays.asList(TiposDocIden_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,tipos){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }

    public static void SeteaSpinner_SEXO(Spinner spiner,Context ctx)
    {
        final List<String> sexos = new ArrayList<>(Arrays.asList(Sexos_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,sexos){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }


    public static void SeteaSpinner_ALMACENAMIENTO(Spinner spiner,Context ctx)
    {
        final List<String> almacenamientos = new ArrayList<>(Arrays.asList(Almacenamiento_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,almacenamientos){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }


    public static void SeteaSpinner_UNIDADES_MEDIDA(Spinner spiner,Context ctx)
    {
        final List<String> medidas = new ArrayList<>(Arrays.asList(UnidadesMedida_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,medidas){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }


    public static void SeteaSpinner_TIPO_MONEDA(Spinner spiner,Context ctx)
    {
        final List<String> monedas = new ArrayList<>(Arrays.asList(TipoMoneda_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,monedas){
            @Override
            public boolean isEnabled(int position){
              /*  if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }*/
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
            /*    if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }*/
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }

    public static void SeteaSpinner_TIPO_IMPRESION(Spinner spiner,Context ctx)
    {
        final List<String> IMPRSION = new ArrayList<>(Arrays.asList(TipoImpresion_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,IMPRSION){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }




    public static void SeteaSpinner_PERIODOS(Spinner spiner,Context ctx)
    {
        final List<String> IMPRSION = new ArrayList<>(Arrays.asList(Periodos_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,IMPRSION){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }







    public static void SeteaSpinner_CONDICIONPAGO(Spinner spiner,Context ctx)
    {
        final List<String> rubros = new ArrayList<>(Arrays.asList(CondicionPago_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,rubros){
            @Override
            public boolean isEnabled(int position){
          /*      if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
                */
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
              /*  if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }*/
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }




    public static void SeteaSpinner_RUBRO_EMPRESA(Spinner spiner,Context ctx)
    {
        final List<String> rubros = new ArrayList<>(Arrays.asList(RubroNegocio_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,rubros){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }



    public static void SeteaSpinner_MODO(Spinner spiner,Context ctx)
    {
        final List<String> tipos = new ArrayList<>(Arrays.asList(Modos_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,tipos){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }


    public static void SeteaSpinner_EstadoTarea(Spinner spiner,Context ctx)
    {
        final List<String> tipos = new ArrayList<>(Arrays.asList(Estados_array));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                ctx, com.peruapps.tareas.R.layout.spineritem,tipos){
            @Override
            public boolean isEnabled(int position){
                if(position == 0  )
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0  )
                {   // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter1.setDropDownViewResource(com.peruapps.tareas.R.layout.spineritem);
        spiner.setAdapter(spinnerArrayAdapter1);
    }




    //--------------------------------------------------------------------

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //----------------------------------------------------------------



    //-------------------------------------------------------------------------------------
    //LISTAS


    public static  String[] Modos_array = new String[]{
            "Seleccione",
            "ONLINE",
            "OFFLINE"


    };

    public static  String[] Periodos_array = new String[]{
            "Seleccione",
            "ULTIMA SEMANA",
            "ULTIMAS 2 SEMANAS",
            "ULTIMO MES",
            "ULTIMOS 6 MESES"

    };

    public static  String[] PeriodosReportes_array = new String[]{
            "ITEMS",
            "CATEGORIAS",
            "VENDEDORES"


    };

    public static  String[] PeriodosReportesAnual_array = new String[]{
            "VENTAS",
            "ITEMS",
            "CATEGORIAS",
            "VENDEDORES"


    };


    public static  String[] Sexos_array = new String[]{
            "Seleccione",
            "MASCULINO",
            "FEMENINO"
    };

    public static  String[] TipoImpresion_array = new String[]{
            "Seleccione",
            "STANDART",
            "TICKET"

    };

    public static  String[] TiposDocIden_array = new String[]{
            "Seleccione",
            "DNI",
            "RUC",
            "CARNET EXTRANJERÍA",
            "PASAPORTE",
            "OTROS"
    };

    public static  String[] Almacenamiento_array = new String[]{
            "Seleccione",
            "INTERNO",
            "TARJETA SD"
    };

    public static  String[] UnidadesMedida_array = new String[]{
            "Seleccione",
            "UNIDADES",
            "PARES",
            "GRAMOS",
            "KILOGRAMOS",
            "METROS",
            "METROS CÚBICOS",
            "CENTÍMETROS",
            "LITROS",
            "LIBRAS",
            "CAJAS",
            "GALONES",
            "BARRILES",
            "LATAS",
            "MILLARES",
            "COSTALES",
            "TONELADAS LARGAS",
            "TONELADAS MÉTRICAS",
            "OTROS"

    };

    public static  String[] Departamento_array = new String[]{
            "Seleccione",
            "LIMA",
            "AMAZONAS",
            "ANCASH",
            "APURIMAC",
            "",
            "",
    };

    public static  String[] TipoMoneda_array = new String[]{
            "SOLES",
            "DOLARES AMERICANOS"
    };

    public static  String[] RubroNegocio_array = new String[]{
            "Seleccione",
            "Calzado",
            "Consultora",
            "Ropa",
            "Artefactos"
    };


    public static  String[] CondicionPago_array = new String[]{
            "CONTADO",
            "DEPÓSITO EN CUENTA",
            "GIRO",
            "TRANSFERENCIA DE FONDOS",
            "EFECTIVO",
            "ORDEN DE PAGO",
            "TARJETA DE DÉBITO",
            "TARJETA DE CRÉDITO",
            "CHECQUE",
            "OTROS"

    };


    public static  String[] Estados_array = new String[]{
            "Seleccione",
            "TERMINADAS",
            "PENDIENTES"


    };



    public static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

}
