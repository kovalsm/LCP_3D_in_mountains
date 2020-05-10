package sk.upjs.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Uklada udaje o jednom vrchole nacitanom zo vstupneho suboru PLY
 *
 */
public class Vertex implements Comparable<Vertex> {

	private final int idxZPLY;
	private final String riadokZaznamuVrcholaZPLY;
	private final double suradnicaX;
	private final double suradnicaY;
	private final double suradnicaZ;
	private int farbaR;
	private int farbaG;
	private int farbaB;
	private final float suradnicaXNorm;
	private final float suradnicaYNorm;
	private final float suradnicaZNorm;
	private final Set<Integer> susedia = new HashSet<>();
	private final Set<int[]> parySusedovPreTrojuholniky = new HashSet<>();
	private Vertex predchadzajuci = null;

	public Vertex(int idxZPLY, String riadokZaznamuVrcholaZPLY, double suradnicaX, double suradnicaY, double suradnicaZ, int farbaR, int farbaG, int farbaB,
			float suradnicaXNorm, float suradnicaYNorm, float suradnicaZNorm) {
		super();
		this.idxZPLY = idxZPLY;
		this.riadokZaznamuVrcholaZPLY = riadokZaznamuVrcholaZPLY;
		this.suradnicaX = suradnicaX;
		this.suradnicaY = suradnicaY;
		this.suradnicaZ = suradnicaZ;
		this.farbaR = farbaR;
		this.farbaG = farbaG;
		this.farbaB = farbaB;
		this.suradnicaXNorm = suradnicaXNorm;
		this.suradnicaYNorm = suradnicaYNorm;
		this.suradnicaZNorm = suradnicaZNorm;
	}

	public int getIdxZPLY() {
		return idxZPLY;
	}

	/**
	 * @return cely riadok z povodneho suboru PLY s ktoreho boli nacitane udaje o
	 *         vrchole, sluzi na ukladanie informacie o vrchole do vystupneho suboru
	 *         PLY
	 */
	public String getRiadokZaznamuVrcholaZPLY() {
		return riadokZaznamuVrcholaZPLY;
	}

	public double getSuradnicaX() {
		return suradnicaX;
	}

	public double getSuradnicaY() {
		return suradnicaY;
	}

	public double getSuradnicaZ() {
		return suradnicaZ;
	}

	public int getFarbaR() {return farbaR;}

	public int getFarbaG() {return farbaG;}

	public int getFarbaB() {return farbaB;}

	public void setFarbaR(int farba){
		this.farbaR = farba;
	}
	public void setFarbaG(int farba){
		this.farbaG = farba;
	}
	public void setFarbaB(int farba){
		this.farbaB = farba;
	}

	public float getSuradnicaXNorm() {
		return suradnicaXNorm;
	}

	public float getSuradnicaYNorm() {
		return suradnicaYNorm;
	}

	public float getSuradnicaZNorm() {
		return suradnicaZNorm;
	}

	public Vertex getPredchadzajuci(){ return predchadzajuci;}

	public void setPredchadzajuci(Vertex pom){
		this.predchadzajuci = pom;
	}

	/**
	 * @return List indexov susedov vrchola z povodneho suboru PLY
	 */
	public List<Integer> getSusedia() {
		return new ArrayList<>(susedia);
	}

	/**
	 * @return List dvojic susedov vrchola, ktory spolu s tymto vrcholom tvoria
	 *         trojuholnik v 3D modely
	 */
	public List<int[]> getParySusedovPreTrojuholniky() {
		return new ArrayList<>(parySusedovPreTrojuholniky);
	}

	/**
	 * @param sused
	 *            je index suseda vrchola v nacitanom subore PLY
	 */
	public void pridajSuseda(int sused) {
		susedia.add(sused);
	}

	/**
	 * @param parSusedov
	 *            je pole dvoch indexov susedov tohto vrchola s ktorymi tento bod
	 *            tvori trojuholnik v 3D modely
	 */
	public void pridajParSusedov(int[] parSusedov) {
		parySusedovPreTrojuholniky.add(parSusedov);
	}


	@Override
	public String toString() {

		String vrchol = suradnicaX + "||" + suradnicaY + "||" + suradnicaZ + "||" + suradnicaXNorm + "||"
				+ farbaR + "||" + farbaG + "||" + farbaB + "||"
				+ suradnicaYNorm + "||" + suradnicaZNorm + "\n" + susedia.toString() + "\n";

		for (int[] par : parySusedovPreTrojuholniky) {
			vrchol = vrchol + par[0] + "||" + par[1] + "  ";
		}

		return vrchol;
	}

	/**
	 * zoradzuje vrcholy podla suradnice Z na zaciatok dava vrchol z najnizsou
	 * suradnicou Z a ak maju rovnaku ale nie je to ten isty vrchol tak ich nejak
	 * zaradi (koli TreeSet)
	 */
	@Override
	public int compareTo(Vertex o) {

		if (this.getSuradnicaZ() > o.getSuradnicaZ()) {
			return 1;
		}

		if (this.getSuradnicaZ() < o.getSuradnicaZ()) {
			return -1;
		}

		if (this.getSuradnicaZ() == o.getSuradnicaZ()) {
			if (!this.equals(o)) {
				return 1;
			}
		}

		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idxZPLY;
		result = prime * result + ((riadokZaznamuVrcholaZPLY == null) ? 0 : riadokZaznamuVrcholaZPLY.hashCode());
		result = prime * result + (int)(suradnicaX);
		result = prime * result + Float.floatToIntBits(suradnicaXNorm);
		result = prime * result + (int)(suradnicaY);

		result = prime * result + Float.floatToIntBits(suradnicaYNorm);
		result = prime * result + (int)(suradnicaZ);
		result = prime * result + Float.floatToIntBits(suradnicaZNorm);
		result = prime * result + ((susedia == null) ? 0 : susedia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (idxZPLY != other.idxZPLY)
			return false;
		if (riadokZaznamuVrcholaZPLY == null) {
			if (other.riadokZaznamuVrcholaZPLY != null)
				return false;
		} else if (!riadokZaznamuVrcholaZPLY.equals(other.riadokZaznamuVrcholaZPLY))
			return false;
		if ((int)(suradnicaX) != (int)(other.suradnicaX))
			return false;
		if (Float.floatToIntBits(suradnicaXNorm) != Float.floatToIntBits(other.suradnicaXNorm))
			return false;
		if ((int)(suradnicaY) != (int)(other.suradnicaY))
			return false;
		if (Float.floatToIntBits(suradnicaYNorm) != Float.floatToIntBits(other.suradnicaYNorm))
			return false;
		if ((int)(suradnicaZ) != (int)(other.suradnicaZ))
			return false;
		if (Float.floatToIntBits(suradnicaZNorm) != Float.floatToIntBits(other.suradnicaZNorm))
			return false;
		if (susedia == null) {
			if (other.susedia != null)
				return false;
		} else if (!susedia.equals(other.susedia))
			return false;
		return true;
	}

}
