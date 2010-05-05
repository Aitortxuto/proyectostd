package server.command;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import server.ClientRequest;
import server.GpsAccess;

public class GetValAct extends Command
{
	@Override
	public String command(ClientRequest cr, String[] params){
		String respuesta = "";
		if (cr.getState()==2){
			if (params.length>0){				
				if(cr.getSensores().containsKey(params[0])){
					if(cr.getSensores().get(params[0]).isOn()){
						String sLongitud = "";
						String sLatitud = "";
						if (cr.isGps()){
							DecimalFormat dec = new DecimalFormat("###.######");
							double longitud = (Math.random() * 180);
							double latitud = (Math.random() * 180);
							sLongitud = dec.format(longitud)+"N";
							sLatitud = dec.format(latitud)+"E";
						}else{
							String coordenadas;
							GpsAccess gps = new GpsAccess();
							gps.connect("127.0.0.1");
							gps.user();
							gps.pass();
							coordenadas = gps.getCoor("3053");
							sLongitud = coordenadas.split("-")[0];
							sLatitud = coordenadas.split("-")[1];
						}
						int valor = (int) (Math.random()*100);
						Calendar calendario = new GregorianCalendar();
						String fecha =Integer.toString(calendario.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(calendario.get(Calendar.MONTH)) +"/"+Integer.toString(calendario.get(Calendar.YEAR));
						String hora= Integer.toString(calendario.get(Calendar.HOUR)) + ":"+Integer.toString(calendario.get(Calendar.MINUTE))+":"+Integer.toString(calendario.get(Calendar.SECOND));
						respuesta = "114 OK "+ fecha +";"+ hora +";"+sLongitud +"-"+sLatitud+";"+valor +CRLF;
						try {
							cr.getDbm().executeInsert("INSERT INTO historico (sensor_id,fecha,hora,latitud,longitud,valor) VALUES('"+params[0]+"','"+fecha+"','"+hora+"','"+sLatitud+"','"+sLongitud+"',"+valor+")");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else
						respuesta = "416 ERR Sensor en OFF" + CRLF;
				}else
					respuesta = "414 Sensor desconocido." + CRLF;
			}else
				respuesta = "415 Falta parámetro id_sensor" + CRLF;
		}else{
			respuesta = "450 ERR Comando no válido" + CRLF;
		}
		return respuesta;
	}
}
