package me.mkweb.ipcalculator.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.mkweb.ipcalculator.exception.IPNotValidException;

public class IP {

	private String symbolicIP;
	private String symbolicSubnetmask;
	private String IpCidr;
	private long numericIP;
	private long numericSubnetmask;
	private int subnetBits;
	private long hostspernet;
	
	public IP() {
		super();
	}

	public IP(String ip, String subnetmask) throws IPNotValidException {

		// Regex Pattern from:
		// http://stackoverflow.com/questions/15875013/extract-ip-addresses-from-strings-using-regex

		Pattern p = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		Matcher ipm = p.matcher(ip);
		Matcher subm = p.matcher(subnetmask);

		if (ipm.matches() && subm.matches()) {
			this.symbolicIP = ip;
			this.symbolicSubnetmask = subnetmask;

			this.numericIP = calculateNumericIP(ip);
			this.numericSubnetmask = calculateNumericIP(subnetmask);

			calculateSubnetBits();
			calculateHostsperNet();
		} else {
			throw new IPNotValidException("IP is not valid!");
		}
	}

	/**
	 * Calculates a numeric IP-Address from a given String.
	 * 
	 * @param ip
	 *            IP-Address as a String
	 * @return numericAddress
	 */
	protected long calculateNumericIP(String ip) {
		long numericIP = 0;
		for (int i = 0; i < 4; i++) {
			int part = Integer.parseInt(ip.split("[.]")[i]);
			numericIP += part * (Math.pow(256, (3 - i)));
		}
		return numericIP;
	}

	/**
	 * Calculates symbolic IP address, e.g. 182384908 => 10.222.249.12
	 * 
	 * @param numericSubnet
	 * 
	 * @return symbolicAddress
	 */
	protected String calculateSymbolicIP(long numericSubnet) {
		String ip = getIpBinaryString(numericSubnet);
		StringBuilder numericalIp = new StringBuilder();

		for (int i = 0; i < 4; i++) {
			String part = i != 3 ? ip.substring(i * 8, 8 + i * 8) : ip.substring(i * 8);
			numericalIp.append(Integer.parseInt(part, 2));
			if (i != 3)
				numericalIp.append(".");
		}
		return numericalIp.toString();
	}
	
	/**
	 * Calculates and sets the symbolic and the numeric Subnetmask from the given CIDR Notation
	 * @param subnetbits
	 */
	protected void calculateSubnetFromCidr(int subnetbits) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 32; i++) {
			if (subnetbits > 0) {
				sb.append("1");
				subnetbits--;
			}
			else {
				sb.append("0");
			}
		}
		this.numericSubnetmask = Long.parseLong(sb.toString(), 2);
		this.symbolicSubnetmask = calculateSymbolicIP(this.numericSubnetmask);
	}

	/**
	 * sets the CIDR-Notation (/32) for the certain subnetmask
	 */
	private void calculateSubnetBits() {
		String binary = getIpBinaryString(numericSubnetmask);

		String[] part = binary.split("");
		int i = 0;
		for (String p : part) {
			if (p.equals("1"))
				i++;
		}
		this.subnetBits = i;

	}

	/**
	 * Returns the subnet address as used in the Integration Engine.
	 * 
	 * @return
	 */
	protected String subnetAddress() {
		long numericSubnet = numericIP & numericSubnetmask;
		String symbolicSubnet = calculateSymbolicIP(numericSubnet);

		return symbolicSubnet + "/" + this.subnetBits;
	}

	/**
	 * Returns the binary String of the given numerical IP address
	 * 
	 * @param numericIp
	 * @return binaryIP
	 * 
	 */
	private String getIpBinaryString(long numericIp) {
		StringBuilder binIp = new StringBuilder(Long.toBinaryString(numericIp));
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 32 - binIp.length(); i++) {

			sb.append("0");
		}
		sb.append(binIp);

		return sb.toString();
	}

	/**
	 * Calculates the number of hosts in the subnet. (-2 for Broadcast and
	 * network addresss)
	 */
	private void calculateHostsperNet() {
		this.hostspernet = (long) Math.pow(2, 32 - this.subnetBits) - 2;
	}

	/**
	 * validtes the given IP-Address with a Regular Expression Subnetmask works
	 * too
	 * 
	 * @param ip
	 * @throws IPNotValidException
	 */
	private boolean validateIpAddress(String ip) {
		Pattern p = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		Matcher ipm = p.matcher(ip);

		if (ipm.matches()) {
			return true;
		}
		return false;
	}

	public void validateIpCidr(String ipcidr) throws IPNotValidException {
		String[] tmp = ipcidr.split("[/]");
		setSymbolicIP(tmp[0]);
		if (tmp.length > 1) {
			setSubnetBitsAsString(tmp[1]);
		}
		else {
			throw new IPNotValidException("CIDR-Notation fehlt!");
		}
		
		this.IpCidr = ipcidr;
	
	}

	/* getters and setters */

	public String getSymbolicIP() {
		return symbolicIP;
	}

	public void setSymbolicIP(String symbolicIP) throws IPNotValidException {
		if (validateIpAddress(symbolicIP)) {
			this.symbolicIP = symbolicIP;
			this.numericIP = calculateNumericIP(symbolicIP);
			calculateSubnetBits();
			this.IpCidr = subnetAddress();
			calculateHostsperNet();
		} else {
			throw new IPNotValidException("IP Addresse ist nicht valid!");
		}
	}

	public String getSymbolicSubnetmask() {
		return symbolicSubnetmask;
	}

	public void setSymbolicSubnetmask(String symbolicSubnetmask) throws IPNotValidException {
		if (validateIpAddress(symbolicSubnetmask)) {
			this.symbolicSubnetmask = symbolicSubnetmask;
			this.numericSubnetmask = calculateNumericIP(symbolicSubnetmask);
			calculateSubnetBits();
			this.IpCidr = subnetAddress();
			calculateHostsperNet();
		} else {
			throw new IPNotValidException("Subnetmaske ist nicht valid!");
		}
	}

	public long getNumericIP() {
		return numericIP;
	}

	public void setNumericIP(long numericIP) {
		this.numericIP = numericIP;
		this.symbolicIP = calculateSymbolicIP(numericIP);
		calculateSubnetBits();
		this.IpCidr = subnetAddress();
		calculateHostsperNet();
	}

	public long getNumericSubnetmask() {
		return numericSubnetmask;
	}

	public void setNumericSubnetmask(long numericSubnetmask) {
		this.numericSubnetmask = numericSubnetmask;
		this.symbolicSubnetmask = calculateSymbolicIP(numericSubnetmask);
		calculateSubnetBits();
		this.IpCidr = subnetAddress();
		calculateHostsperNet();
	}

	public int getSubnetBits() {
		return subnetBits;
	}

	public void setSubnetBits(int subnetBits) {
		this.subnetBits = subnetBits;
	}

	public long getHostspernet() {
		return hostspernet;
	}

	public String getSymbolicIpCidr() {
		return IpCidr;
	}

	public void setSymbolicIpCidr(String IpCidr) {
		this.IpCidr = IpCidr;
	}

	public void setSubnetBitsAsString(String cidrtxt) throws IPNotValidException {
		int tmp = Integer.parseInt(cidrtxt);
		if (tmp >= 0 && tmp <= 31) {
			subnetBits = tmp;
			calculateSubnetFromCidr(subnetBits);
			IpCidr = subnetAddress();
			calculateHostsperNet();
		} else {
			throw new IPNotValidException("CIDR-Notation ist nicht valid!");
		}
	}
}
