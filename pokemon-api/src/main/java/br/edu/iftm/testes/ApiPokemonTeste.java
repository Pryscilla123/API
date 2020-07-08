package br.edu.iftm.testes;

import java.io.IOException;

import javax.swing.JFrame;

public class ApiPokemonTeste {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame pokeTela = new JFrame();
		pokeTela.setBounds(250, 100, 700, 500);
		pokeTela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pokeTela.setVisible(true);
		
		ApiPokemonJanela poke = new ApiPokemonJanela();
		pokeTela.add(poke);
	}

}