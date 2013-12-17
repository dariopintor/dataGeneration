/***************************************************************************
                          l_string.h  -  description
                             -------------------
    begin                : Dom Jul 6 2003
    copyright            : (C) 2003 by Luciano Petinati Ferreira
    email                : petinat@inf.ufpr.br
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

#ifndef L_STRING_H
#define L_STRING_H


/**Tipo String pr�prio.
  *@author Luciano Petinati Ferreira
  */
#include<iostream>
#include<ostream>
#include"diversos.h"

using namespace std;

class l_string {

public:
	l_string();
	~l_string();
  /** Sobrecarga do operador de atribui��o. */
  l_string operator=(l_string dir);
  /** Sobrecarga de operador atribui��o para l_string com char *. */
  l_string operator=(char * dir);
  /** Sobrecarga de extrator <<. */
  friend ostream &operator<<(ostream & canal, l_string obj);
  /** Construtor de c�pia - necess�rio devido a classe alocar mem�ria dinamicamente na constru��o do objeto. */
   l_string(const l_string & obj);
  /** M�todo para obter char * a partir de l_string. */
  char * getValor();
  /** M�todo para limpar caracteres em branco das extremidades de um l_string. */
  int trim();
  /** M�todo que reduz o valor do string para as dimens�es passadas por argumento.
inic >= 0
fim <= tamanho do string == tam. */
  int substring( int inic, int fim);
  /** Sobrecarga de operador de concatena��o com l_string. */
  l_string operator+ ( l_string dir );
  /** Sobrecarga de operador de concatena��o com char *. */
  l_string operator+ ( char * dir );
//private: // Private attributes
  /** tam armazena o tamanho para valor */
  long tam;
  /** valor possui o conte�do do string */
  char * valor;

};

#endif
