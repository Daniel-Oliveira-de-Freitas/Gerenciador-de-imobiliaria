package application;

import java.io.File;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class SistemaImobiliarioController {
	String caminho;

	@FXML
	private Label lblconf;

	@FXML
	private Pane agd, alteres;

	@FXML
	private ListView<String> lista;

	@FXML
	private TextField pesquisa,a1,a2,a3;

	@FXML
	void limpa(ActionEvent event) {
		lista.getItems().clear();
		lblconf.setText("Sistema Imobiliario GP3");
	}

	@FXML
	void open(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Escolha um arquivo .pl");
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PL", "*.pl*"));
			File file = fc.showOpenDialog(null);
			caminho = file.getAbsolutePath();
			Query q = new Query("consult", new Term[] { new Atom(caminho) });
			lblconf.setText("consult " + (q.hasSolution() ? "succeeded" : "failed"));

		} catch (Exception e) {
			MSA("Arquivo Invalido");
		}
	}

	@FXML
	void b1(ActionEvent event) {
		try {
			Variable X = new Variable("X");
			Variable LC = new Variable("LC");
			Query q1 = new Query("listarvendedores", new Term[] { X, LC });
			Map<String, Term> solution;
			while (q1.hasNext()) {
				solution = q1.nextSolution();
				lista.getItems().addAll("Imobiliaria: " + solution.get("X").toString() + " \nVendedores: "
						+ solution.get("LC").toString().replaceAll("[\\[\\]]", "") + "\n");
			}
			lblconf.setText("Lista todos os vendedores ");
		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");
		}

	}

	@FXML
	void b2(ActionEvent event) {
		try {
			Variable X = new Variable("X");
			Variable LCC = new Variable("LCC");
			Query q2 = new Query("listarcodCliente", new Term[] { X, LCC });
			Map<String, Term> solution;
			while (q2.hasNext()) {
				solution = q2.nextSolution();
				lista.getItems().addAll("Imobiliaria: " + solution.get("X").toString() + " \nCodigo dos Clientes: "
						+ solution.get("LCC").toString().replaceAll("[\\[\\]]", "") + "\n");
			}
			lblconf.setText("Lista clientes por codigo ");
		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");
		}
	}

	@FXML
	void b3(ActionEvent event) throws Exception {
		try {
			int f;
			Variable X = new Variable("X");
			Variable D = new Variable("D");
			Variable C = new Variable("C");

			if (!pesquisa.getText().isEmpty()) {
				f = Integer.parseInt(pesquisa.getText());
				org.jpl7.Integer i = new org.jpl7.Integer(f);
				Query q3 = new Query("listardadoscliente", new Term[] { X, i, D });
				Map<String, Term> solution;
				while (q3.hasNext()) {
					solution = q3.nextSolution();
					lista.getItems().addAll("Imobiliaria: " + solution.get("X").toString() + " \nDados dos Clientes: "
							+ solution.get("D").toString().replaceAll("[\\[\\]]", "") + "\n");
				}
			} else {
				Query q4 = new Query("listardadoscliente", new Term[] { X, C, D });
				Map<String, Term> solution;
				while (q4.hasNext()) {
					solution = q4.nextSolution();
					lista.getItems().addAll("Imobiliaria: " + solution.get("X").toString() + " \nDados dos Clientes: "
							+ solution.get("D").toString().replaceAll("[\\[\\]]", "") + "\n");
				}
			}
			lblconf.setText("Lista dados dos clientes ");

		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");
		}

	}

	@FXML
	void b4(ActionEvent event) {
		try {
			Variable X = new Variable("X");
			Variable LM = new Variable("LM");
			Query q5 = new Query("listarvendas", new Term[] { new Atom(pesquisa.getText()), LM });
			Map<String, Term> solution;
			while (q5.hasNext()) {
				solution = q5.nextSolution();
				lista.getItems()
						.addAll("Tipos de Imoveis: " + solution.get("LM").toString().replaceAll("[\\[\\]]", "") + "\n");
			}
			lblconf.setText("Lista tipos imóveis de cada imobiliária ");
		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");

		}
	}

	@FXML
	void b5(ActionEvent event) {
		try {
			Variable X = new Variable("X");
			Variable P = new Variable("P");
			Variable LCC = new Variable("LCC");
			Query q6 = new Query("listarprofissao", new Term[] { X, new Atom(pesquisa.getText()), LCC });
			Map<String, Term> solution;
			while (q6.hasNext()) {
				solution = q6.nextSolution();
				lista.getItems().addAll("Imobiliaria: " + solution.get("X").toString() + " \nProfissão do Clientes: "
						+ solution.get("LCC").toString().replaceAll("[\\[\\]]", "") + "\n");
			}
			lblconf.setText("Lista clientes por profissão ");
		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");

		}
	}

	@FXML
	void b6(ActionEvent event) {
		try {
			Variable X = new Variable("X");
			Variable Med = new Variable("Med");
			Query q7 = new Query("preco_medio", new Term[] { X, Med });
			Map<String, Term> solution;
			while (q7.hasNext()) {
				solution = q7.nextSolution();
				lista.getItems().addAll("Imobiliaria: " + solution.get("X").toString() + " \nValor Medio de vendas: "
						+ solution.get("Med").toString().replaceAll("[\\[\\]]", "") + "\n");
			}
			lblconf.setText("Valor médio de venda");
		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");
		}
	}

	@FXML
	void b7(ActionEvent event) {
		try {
			Variable NomeDaLista = new Variable("NomeDaLista");
			Query q8 = new Query("listarValorImobiliariadescrecente", new Term[] { NomeDaLista });
			Map<String, Term> solution;
			while (q8.hasNext()) {
				solution = q8.nextSolution();
				lista.getItems().addAll("Imobiliaria: " + solution.get("NomeDaLista").toString() + "\n");
			}
			lblconf.setText("Imobiliaria com maior venda decrescente");
		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");
		}
	}

	@FXML
	void b8(ActionEvent event) {
		try {
			Variable NomeDaLista = new Variable("NomeDaLista");
			Query q9 = new Query("listarvendedorescrescente", new Term[] { NomeDaLista });
			Map<String, Term> solution;
			while (q9.hasNext()) {
				solution = q9.nextSolution();
				lista.getItems().addAll("Imobiliaria: " + solution.get("NomeDaLista").toString() + "\n");
			}
			lblconf.setText("Vendedores por ordem crescente no valor de vendas");
		} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");
		}
	}

	public void MSA(String k) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("SISTEMA IMOBILIARIO GP3");
		alert.setContentText(k);
		alert.setHeaderText("Atenção");
		alert.showAndWait();
	}

	@FXML
	void infoG(ActionEvent event) {
		agd.setVisible(true);
	}

	@FXML
	void voltar(ActionEvent event) {
		agd.setVisible(false);
	}

	@FXML
	void next(ActionEvent event) {
		alteres.setVisible(true);
	}

	@FXML
	void back(ActionEvent event) {
		alteres.setVisible(false);
	}

	@FXML
	void alterar(ActionEvent event) {
		try {
			int f , g;
			String Prof;
 			Variable D = new Variable("D");
			f =Integer.parseInt(a1.getText());
			g =Integer.parseInt(a2.getText());
			Prof = a3.getText(); 
			org.jpl7.Integer Cod = new org.jpl7.Integer(f);
			org.jpl7.Integer Idade = new org.jpl7.Integer(g);			
			Query q10 = new Query("alterarid", new Term[] {Cod,Idade,new Atom(Prof),D});
			Map<String, Term> solution;
			
			while (q10.hasNext()) {
				solution = q10.nextSolution();
				lista.getItems().addAll("Dados alterados do Cliente: "+ solution.get("D").toString().replaceAll("[\\[\\]]", ""));
				
				}
			lblconf.setText("Dados do cliente alterado");
			
	} catch (Exception e) {
			MSA("Escolha um aquivo .pl para pesquisar");
		}
	}

}
