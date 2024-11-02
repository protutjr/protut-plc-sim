package br.com.protut;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.data.ModbusCoils;
import com.intelligt.modbus.jlibmodbus.data.ModbusHoldingRegisters;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.exception.IllegalDataAddressException;
import com.intelligt.modbus.jlibmodbus.exception.IllegalDataValueException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.request.ReadHoldingRegistersRequest;
import com.intelligt.modbus.jlibmodbus.msg.response.ReadHoldingRegistersResponse;
import com.intelligt.modbus.jlibmodbus.slave.ModbusSlaveFactory;
import com.intelligt.modbus.jlibmodbus.slave.ModbusSlaveTCP;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.intelligt.modbus.jlibmodbus.utils.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.Observer;
import java.util.Random;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class PlcSimulator {
    private static ModbusSlaveTCP slave;
    private static int tcp_port = 502;
    private static int slave_id = 2;
	private static boolean use_keep_alive = false;
	

    public static void init() throws Exception {
		Modbus.setLogLevel(Modbus.LogLevel.LEVEL_RELEASE);
		TcpParameters tcpParameters = new TcpParameters();
		
		// Carregar os parâmetros da comunicação Modbus
		String tomcatBase = System.getProperty("catalina.base");
		String fs = File.separator;		
		String webappFolder = tomcatBase + fs + "webapps" + fs + "clp" + fs + "WEB-INF" + fs + "application.properties";
		Properties prop = new Properties();
		System.out.println(webappFolder);
		
		try (InputStream input = new FileInputStream(webappFolder)) {
            prop.load(input);            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		// Parâmetros de comunicação
        tcp_port = Integer.parseInt(prop.getProperty("tcp.port", "502"));
        slave_id = Integer.parseInt(prop.getProperty("slave.id", "2"));
		use_keep_alive = prop.getProperty("use.keep.alive", "true").equals("true");	
		
		tcpParameters.setHost(InetAddress.getLocalHost());
		tcpParameters.setPort(tcp_port);
		tcpParameters.setKeepAlive(use_keep_alive);
		
		System.out.println(prop.getProperty("tcp.port", "502"));
		
		slave = (ModbusSlaveTCP) ModbusSlaveFactory.createModbusSlaveTCP(tcpParameters);            
		
		// Endereço do escravo Modbus
		slave.setServerAddress(slave_id);
		slave.setReadTimeout(0);


		setDefaultValues();

		Modbus.setAutoIncrementTransactionId(true);

		slave.listen();
    }
	
	public static void terminate() {
		if (slave != null) {
			try {
				slave.shutdown();
				slave = null;
			} catch (ModbusIOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isRunning() {
		if (slave != null) return true;		
		else return false;
	}

	public static void setDefaultValues() {
		int quantity = 1000;
		ModbusCoils coils = new ModbusCoils(quantity);
		ModbusCoils discreteInputs = new ModbusCoils(quantity);
		ModbusHoldingRegisters holdingRegisters = new ModbusHoldingRegisters(quantity);
		ModbusHoldingRegisters inputRegisters = new ModbusHoldingRegisters(quantity);
		
		try {
			for (int i = 0; i < quantity; i++) {
				coils.set(i, false);
				discreteInputs.set(i, false);			
				holdingRegisters.set(i, 0);
				inputRegisters.set(i, 0);
			}
			
			// Ligar as saídas digitais pares
			coils.set(0, true);
			coils.set(2, true);

			// Ligar as entradas digitais ímpares
			discreteInputs.set(1, true);
			discreteInputs.set(3, true);
			discreteInputs.set(5, true);
			discreteInputs.set(7, true);

			// Entrada analógica 0 -> Tensão da rede
			inputRegisters.set(0, 127);
			// Entrada analógica 1 -> Temperatura
			inputRegisters.setFloat32At(1, (float) 20.3);

			// Saídas analógicas 0 e 1 -> Porcentagens
			holdingRegisters.set(0, 20);
			holdingRegisters.set(1, 65);

			// Associando valores ao escravo Modbus
			slave.getDataHolder().setCoils(coils);
			slave.getDataHolder().setDiscreteInputs(discreteInputs);
			slave.getDataHolder().setHoldingRegisters(holdingRegisters);
			slave.getDataHolder().setInputRegisters(inputRegisters);
		} catch (IllegalDataAddressException e) {
			e.printStackTrace();
		} catch (IllegalDataValueException e) {
			e.printStackTrace();
		}
	}

	public static void setRandomValues() {
		ModbusCoils coils = slave.getDataHolder().getCoils();
		ModbusCoils discreteInputs = slave.getDataHolder().getDiscreteInputs();
		ModbusHoldingRegisters holdingRegisters = slave.getDataHolder().getHoldingRegisters();
		ModbusHoldingRegisters inputRegisters = slave.getDataHolder().getInputRegisters();
		
		Random rand = new Random();								
		try {			
			
			for (int i = 0; i < 8; i++) {
				discreteInputs.set(i, rand.nextBoolean());
				if (i < 4)
					coils.set(i, rand.nextBoolean());
			}			

			// Entrada analógica 0 -> Tensão da rede
			int tensao = 121 + rand.nextInt(13);
			inputRegisters.set(0, tensao);
			// Entrada analógica 1 -> Temperatura
			float temperatura = 15 + rand.nextInt(16) + rand.nextFloat();
			inputRegisters.setFloat32At(1, temperatura);
			
			// Saídas analógicas 0 e 1 -> Porcentagens
			holdingRegisters.set(0, rand.nextInt(101));
			holdingRegisters.set(1, rand.nextInt(101));

			// Associando valores ao escravo Modbus
			slave.getDataHolder().setCoils(coils);
			slave.getDataHolder().setDiscreteInputs(discreteInputs);
			slave.getDataHolder().setHoldingRegisters(holdingRegisters);
			slave.getDataHolder().setInputRegisters(inputRegisters);			
		} catch (IllegalDataAddressException e) {
			e.printStackTrace();
		} catch (IllegalDataValueException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Boolean> getCoils() {
		ModbusCoils coils = slave.getDataHolder().getCoils();
		List<Boolean> coilValues = new ArrayList<>();
		
		try {
			for (int i = 0; i < 4; i++) {
				coilValues.add(coils.get(i));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return coilValues;
	}

	public static List<Boolean> getDiscreteInputs() {
		ModbusCoils discreteInputs = slave.getDataHolder().getDiscreteInputs();
		List<Boolean> discreteInputValues = new ArrayList<>();
		
		try {
			for (int i = 0; i < 8; i++) {
				discreteInputValues.add(discreteInputs.get(i));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return discreteInputValues;
	}

	public static List<Double> getHoldingRegisters() {
		ModbusHoldingRegisters holdingRegisters = slave.getDataHolder().getHoldingRegisters();
		List<Double> holdingRegisterValues = new ArrayList<>();
		
		try {
			for (int i = 0; i < 2; i++) {
				holdingRegisterValues.add(holdingRegisters.get(i).doubleValue());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return holdingRegisterValues;
	}

	public static List<Double> getInputRegisters() {
		ModbusHoldingRegisters inputRegisters = slave.getDataHolder().getInputRegisters();
		List<Double> inputRegisterValues = new ArrayList<>();
		
		try {
			inputRegisterValues.add(inputRegisters.get(0).doubleValue());
			inputRegisterValues.add(new Double(inputRegisters.getFloat32At(1)));
		} catch(Exception e) {
			e.printStackTrace();
		}

		return inputRegisterValues;
	}
	
	public static int getTcpPort() {
		return tcp_port;
	}
	
	public static int getSlaveId() {
		return slave_id;
	}
	
	public static boolean getKeepAlive() {
		return use_keep_alive;
	}

}
