package server.command;

import java.sql.ResultSet;

import server.ClientRequest;

public class Pass extends Command
{
	@Override
	public String command(ClientRequest cr, String[] params){
		String respuesta = "";
		if (cr.getState()==1){
			if (params.length == 0) {
				respuesta = "403 ERR Falta la clave" + CRLF;
			}else{
				String user = cr.getUser();
				String realUser = "";
				String realPass = "";
				try{
					ResultSet rs = cr.getDbm().executeSelect("SELECT * FROM usuario WHERE nombre='"+user+"'");			
					if(rs.next()){
						realUser = rs.getString("nombre");
						realPass = rs.getString("pass");
					}
					rs.close();
				}catch (Exception e){
					e.printStackTrace();
				}
				if (user.equals(realUser) && params[0].equals(realPass))
				{
					cr.setState(2);
					respuesta =  "202 OK Bienvenido al sistema" + CRLF;
				}else{
					cr.setState(0);
					cr.setUser("");
					respuesta =  "402 ERR La clave es incorrecta. Introduzca usuario" + CRLF;
					
				}
			}
		}else respuesta = "450 ERR Comando no válido" + CRLF;		
		return respuesta;
	}
}
