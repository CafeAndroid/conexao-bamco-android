<?php

  //Criacao de um objeto PHP
  class Usuario{
  	var $id;
  	var $nome;
  	var $senha;
  	var $url_imgs;
  }

  //Linhas responsaveis por tratamentos de alguns erros no lado do serividor
  ini_set('display_errors', 1);
  error_reporting (E_ALL);

  //grupo de variaveis que pegaram os valores enviados do android
  $usuario = new Usuario();
  $usuario->nome = isset( $_POST['nome'] ) ? $_POST['nome'] : '' ;
  $usuario->senha = isset( $_POST['senha'] ) ? $_POST['senha'] : '' ;

  $host = '127.0.0.1:3306';
  $user = 'root';
  $password = '';
  $conexao = mysql_connect($host,$user,$password) or die (mysql_error());

  //Verifica se a conexao foi realizada
  if (!$conexao)
  	die('Nâo foi possí­vel conectar: ' . mysql_error());
  else{
  	echo "Conexão aceita<br><br>";

  	mysql_select_db('test', $conexao);
  	$sql = "select * from usuarios";
  	$resultado = mysql_query($sql) or die ("Erro no banco. " . mysql_error());

  	$lista = array();
  	while($linha = mysql_fetch_object($resultado)){
  		array_push($lista, $linha);
  	}

  	//Gerar o JSON de retorno
  	echo json_encode(array_values($lista));
  }
?>
