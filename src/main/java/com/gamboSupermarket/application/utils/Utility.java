package com.gamboSupermarket.application.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

	private static final Logger logger = LoggerFactory.getLogger(Utility.class);

	/*
	 * public static byte[] compressBytes(byte[] data) {
	 * 
	 * Deflater deflater = new Deflater(); deflater.setInput(data);
	 * deflater.finish(); ByteArrayOutputStream outputStream = new
	 * ByteArrayOutputStream(data.length); byte[] buffer = new byte[1024]; while
	 * (!deflater.finished()) { int count = deflater.deflate(buffer);
	 * outputStream.write(buffer, 0, count); } try { outputStream.close(); } catch
	 * (IOException e) { } logger.info("Compressed Image Byte Size - ",
	 * outputStream.toByteArray().length); return outputStream.toByteArray();
	 * 
	 * }
	 * 
	 * // uncompress the image bytes before returning it to the angular application
	 * public static byte[] decompressBytes(byte[] data) { Inflater inflater = new
	 * Inflater(); inflater.setInput(data); ByteArrayOutputStream outputStream = new
	 * ByteArrayOutputStream(data.length); byte[] buffer = new byte[1024]; try {
	 * while (!inflater.finished()) { int count = inflater.inflate(buffer);
	 * outputStream.write(buffer, 0, count); } outputStream.close(); } catch
	 * (IOException ioe) { } catch (DataFormatException e) { } return
	 * outputStream.toByteArray(); }
	 */
	
	/**
	 * This method returns true if the collection is null or is empty.
	 * @param collection
	 * @return true | false
	 */
	public static boolean isEmpty( Collection<?> collection ){
		if( collection == null || collection.isEmpty() ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true of the map is null or is empty.
	 * @param map
	 * @return true | false
	 */
	public static boolean isEmpty( Map<?, ?> map ){
		if( map == null || map.isEmpty() ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the objet is null.
	 * @param object
	 * @return true | false
	 */
	public static boolean isEmpty( Object object ){
		if( object == null ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input array is null or its length is zero.
	 * @param array
	 * @return true | false
	 */
	public static boolean isEmpty( Object[] array ){
		if( array == null || array.length == 0 ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input string is null or its length is zero.
	 * @param string
	 * @return true | false
	 */
	public static boolean isEmpty( String string ){
		if( string == null || string.trim().length() == 0 ){
			return true;
		}
		return false;
	}

	/*
	 * public static String getUserName() { UserDetails userDetails = (UserDetails)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); return
	 * userDetails.getUsername();
	 * 
	 * }
	 */
}
