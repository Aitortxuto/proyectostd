package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * <p>
 * Title: Practica 2 WebServer
 * </p>
 * <p>
 * Description: Hace transparente la funcionalidad b�sica de la clase Socket
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ESIDE
 * </p>
 * 
 * @author Unai Hernandez Jayo & Cia
 * @version 1.0
 */

public class SocketManager
{
	private Socket mySocket;

	private DataOutputStream bufferEscritura;
	private BufferedReader bufferLectura;

	public SocketManager(Socket sock) throws IOException
	{
		this.mySocket = sock;
		inicializaStreams();
	}

	/**
	 * @param address
	 *            InetAddress
	 * @param port
	 *            int numero de puerto
	 * @throws IOException
	 */
	public SocketManager(InetAddress address, int port) throws IOException
	{
		mySocket = new Socket(address, port);
		inicializaStreams();
	}

	/**
	 * @param host
	 *            String nombre del servidor al que se conecta
	 * @param port
	 *            int puerto de conexion
	 * @throws IOException
	 */
	public SocketManager(String host, int port) throws IOException
	{
		mySocket = new Socket(host, port);
		inicializaStreams();
	}

	/**
	 * Inicializaci�n de los bufferes de lectura y escritura del socket
	 * 
	 * @throws IOException
	 */
	public void inicializaStreams() throws IOException
	{
		bufferEscritura = new DataOutputStream(mySocket.getOutputStream());
		bufferLectura = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	}

	public void cerrarStreams() throws IOException
	{
		bufferEscritura.close();
		bufferLectura.close();
	}

	public void cerrarSocket() throws IOException
	{
		mySocket.close();
	}

	/**
	 * @return String
	 * @throws IOException
	 */
	public String leer() throws IOException
	{
		return (bufferLectura.readLine());
	}

	public void escribir(String contenido) throws IOException
	{
		bufferEscritura.writeBytes(contenido);
	}

	public void escribir(byte[] buffer, int bytes) throws IOException
	{
		bufferEscritura.write(buffer, 0, bytes);
	}
}
