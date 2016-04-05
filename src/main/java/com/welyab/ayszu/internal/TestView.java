package com.welyab.ayszu.internal;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

@Named
@ViewScoped
public class TestView implements Serializable {

	private String valor;

	public void mudouValor(ValueChangeEvent event) {
		BigInteger integer = new BigInteger("hexadecimal value", 16);
		System.out.println(event);
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public static void main(String[] args) {
		// the arrays
		int a1[] = {1, 2, 3};
		int a2[] = {2, 3, 1};

		// copy array to not modify originals
		int[] a1Temp = Arrays.copyOf(a1, a2.length);
		int[] a2Temp = Arrays.copyOf(a2, a2.length);

		// sort the temp arrays
		Arrays.sort(a1Temp);
		Arrays.sort(a2Temp);

		// check if temp arrays are equals
		boolean test = Arrays.equals(a1Temp, a2Temp);
		System.out.println(test);

		// to large arrays, these operations have a coast 2xO(n log n)+3xn
	}
}
