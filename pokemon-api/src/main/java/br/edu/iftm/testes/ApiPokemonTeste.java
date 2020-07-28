package br.edu.iftm.testes;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ApiPokemonTeste {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame pokeTela = new JFrame();
		pokeTela.setBounds(340, 150, 590, 405);
		pokeTela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pokeTela.setLayout(null);
		pokeTela.setResizable(false);
		ApiPokemonJanela poke = new ApiPokemonJanela();
		pokeTela.add(poke);
		
		pokeTela.setVisible(true);
	}

}