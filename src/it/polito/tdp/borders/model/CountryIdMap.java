package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

public class CountryIdMap {

	private Map<Integer, Country> map = new HashMap<>();

	public Country getCode(int cCode) {
		return map.get(cCode);
	}
	
	public Country getCountry(Country c) {
		Country vecchio = map.get(c.getcCode());
		if (vecchio == null) {
			map.put(c.getcCode(), c);
			return c;
		}
		return vecchio;
	}
	
	public void put (Country c, int cCode) {
		map.put(cCode, c);
	}
	
}
