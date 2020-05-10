package sk.upjs.pracaSDatami;

import java.util.List;
import java.util.Set;

import sk.upjs.entity.Vertex;

public abstract interface Algoritmy {
	public List<Vertex> najkratsiaCestaDijk();
	public List<Vertex> najkratsiaCestaNaismith();
	public List<Vertex> najkratsiaCestaIrmischer();
	public List<Vertex> najkratsiaCestaTobler();


}
