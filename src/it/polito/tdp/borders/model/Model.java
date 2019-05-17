package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> grafo;
	private BordersDAO bordersDao;
	private CountryIdMap countryIdMap;
	private List<Country> paesi;

	public Model() {
		bordersDao = new BordersDAO();
	}

	public void creaGrafo(int anno) {
		
		countryIdMap = new CountryIdMap();
		paesi = bordersDao.loadAllCountries(countryIdMap);
		
		List<Border> confini = bordersDao.getCountryPairs(countryIdMap, anno);
		
		if(confini.isEmpty())
			throw new RuntimeException("Nessun confine trovato per quell'anno");
	
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		for(Border b : confini) {
			grafo.addVertex(b.getC1());
			grafo.addVertex(b.getC2());
			grafo.addEdge(b.getC1(), b.getC2());
		}
		
		System.out.println("Grafo creato con: " + grafo.vertexSet().size() + " vertici.");
		System.out.println("Grafo creato con: " + grafo.edgeSet().size() + " archi.");
		
		paesi = new ArrayList<>(grafo.vertexSet());
		Collections.sort(paesi);
		
	}
	
	public List<Country> getCountries() {
		if(paesi == null)
			return new ArrayList<Country>();
		
		return paesi;	
	}

	public Map<Country, Integer> getNumeroPaesi() {
		if(grafo == null)
			throw new RuntimeException("Grafo non esistente");
		
		Map<Country, Integer> map = new HashMap<>();
		for(Country c : grafo.vertexSet()) {
			map.put(c, grafo.degreeOf(c));
		}
		
		return map;
	}
	
	public Object getNumberOfConnectedComponents() {
		if(grafo == null)
			throw new RuntimeException("Grafo non esistente");
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(grafo);
		
		return ci.connectedSets().size();
	}

	
	
}
