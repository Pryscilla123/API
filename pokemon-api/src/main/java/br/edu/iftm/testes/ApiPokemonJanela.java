package br.edu.iftm.testes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import br.edu.iftm.api.Api;
import br.edu.iftm.api.JsonParser;

public class ApiPokemonJanela extends JPanel{
	
	private String nomePokemon;
	private String url;
	private int idPokemon;
	private Api api;
	private JSONObject json;
	private JLabel label;
	private JTextArea pokedex;
	private JLabel imagemPokemon;
	private JComboBox pokemons;
	
	public ApiPokemonJanela() throws IOException {
		setBounds(0, 0, 650, 410);
		setBackground(Color.WHITE);
		setLayout(null);
		janela();
		
		JPanel panelEsquerdo = new JPanel();
		panelEsquerdo.setBackground(new Color(100, 149, 237));
		panelEsquerdo.setBounds(0, 0, 211, 371);
		panelEsquerdo.setLayout(null);
		add(panelEsquerdo);
		
		JLabel labelInicio = new JLabel("Seja bem-vindo!");
		labelInicio.setFont(new Font("Impact", Font.PLAIN, 18));
		labelInicio.setBounds(30, 36, 190, 20);
		panelEsquerdo.add(labelInicio);
		
		JLabel labelPokemon = new JLabel("- Pok�mon API -");
		labelPokemon.setForeground(new Color(255, 215, 0));
		labelPokemon.setFont(new Font("Impact", Font.PLAIN, 40));
		labelPokemon.setBounds(265, 29, 253, 75);
		add(labelPokemon);
				
		JLabel labelInformacao = new JLabel("Informa��o do Pok�mon:");
		labelInformacao.setFont(new Font("Stencil", Font.PLAIN, 14));
		labelInformacao.setBounds(220, 152, 192, 23);
		add(labelInformacao);		

	}

	public void janela() throws MalformedURLException, IOException {
		
		pokemons = new JComboBox(listaPokemon());
		pokemons.setBounds(30, 112, 122, 20);
		pokemons.setEditable(false);
		pokemons.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nomePokemon = pokemons.getSelectedItem().toString();
				try {
					remove(imagemPokemon);
					revalidate();
					repaint();
					
					pokedex.setText("");
					pokedex(nomePokemon);
					
					imagemPokemon = pegarIcone(idPokemon);
					add(imagemPokemon);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		label = new JLabel("Nome do Pok�mon:");
		label.setFont(new Font("Stencil", Font.PLAIN, 14));
		label.setBounds(30, 75, 141, 26);
		
		imagemPokemon = pegarIcone(idPokemon);
		
		pokedex = new JTextArea();
		pokedex.setEditable(false);
		pokedex.setBounds(215, 186, 360, 183);	
		pokedex.setFont(new Font("Modern No. 20", Font.PLAIN, 16));
		
		add(pokemons);
		add(imagemPokemon);
		add(pokedex);
		add(label);
		setVisible(true);
	}
	
	private void pokedex(String nomePokemon) throws IOException {
		
		String url = String.format("https://pokeapi.co/api/v2/pokemon/%s", nomePokemon);
		api = new Api(url);
		json = JsonParser.parseToObject(api.executar());
		
		//Id
		idPokemon = json.getInt("id");
		pokedex.append("Id do Pok�mon: " + idPokemon + "\n");
		
		pokedex.append("\n");
		
		//Tipo
		pokedex.append("Tipo do Pok�mon\n");
		JSONArray tipos = json.getJSONArray("types");
		
		for(int i = 0; i < tipos.length(); i ++) {
			JSONObject allTypes = tipos.getJSONObject(i);
			JSONObject type = allTypes.getJSONObject("type");
			pokedex.append("-" + type.getString("name") + "\n");
		}
		
		pokedex.append("\n");
		
		//Habilidades do Pok�mon
		pokedex.append("Habilidades do Pok�mon\n");
		
		JSONArray abilities = json.getJSONArray("abilities");
		
		for(int i = 0; i < abilities.length(); i++) {
			JSONObject abilityCompleta = abilities.getJSONObject(i);
			JSONObject ability = abilityCompleta.getJSONObject("ability");
			pokedex.append("-" + ability.getString("name") + "\n");
		}
	}
	
	private String[] listaPokemon() throws IOException{
		
		String url = "https://pokeapi.co/api/v2/pokemon/";
		api = new Api(url);
		json = JsonParser.parseToObject(api.executar());
		int count = json.getInt("count");
		
		String nomes[] = new String[count];
		
		url = String.format("https://pokeapi.co/api/v2/pokemon?offset=0&limit=%d", count);
		api = new Api(url);
		json = JsonParser.parseToObject(api.executar());
		
		JSONArray results = json.getJSONArray("results");
		
		for(int i = 0; i < results.length(); i++) {
			JSONObject nomesPoke = results.getJSONObject(i);
			nomes[i] = nomesPoke.getString("name");
		}
		
		return nomes;
	}
	
	private static JLabel pegarIcone(int fotoId) throws MalformedURLException, IOException {
		String fotoUrl = String.format("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%d.png", fotoId);
		JLabel icone = new JLabel("");
		Image imagem = ImageIO.read(new URL(fotoUrl));
		int larguraImagem = imagem.getWidth(null);
		int alturaImagem = imagem.getHeight(null);
		icone.setIcon(new ImageIcon(imagem));
		icone.setBounds(450, 100, larguraImagem, alturaImagem);
		return icone;
	}

	public int getIdPokemon() {
		return idPokemon;
	}

	public void setIdPokemon(int idPokemon) {
		this.idPokemon = idPokemon;
	}

}