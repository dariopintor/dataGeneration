/***************************************************************************
                          individuo.h  -  description
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
#include "diversos.h"
#ifndef INDIVIDUO_H
#define INDIVIDUO_H


/**
  *@author Luciano Petinati Ferreira
  */
//#include "diversos.h"
#include "central.h"
#include <math.h>


class individuo {
public:
	individuo(central ** parCtl);
	~individuo();
  /** M�todo que atribui valor para a representa��o de um indiv�duo. */
  int setRepresentacao (char * valor);
  /** M�todo para recuperar o pos-esimo indiv�duo da popula��o. */
  int load (int pos);
  /** M�todo para criar um novo indiv�duo baseado na linha do arquivo e na configura��o fornecida ao framework. */
  int novo (char * linha);
  /** M�todo para criar um novo indiv�duo aleat�rio. */
  int novo();

  /** M�todo que recupera o respectivo valor do bloco passado como argumento.  */
  double decode_block_float ( char * bloco );
  /** M�todo usado para formatar um bloco a partir de um valor passado por argumento. */
  int forma_block_float (char ** res, int limite, char * lido);
  /** M�todo usado para formatar um bloco float aleatoriamente. */
  int forma_block_float_random(char ** res, int limite);

  /** M�todo que recupera o respectivo valor do bloco passado como argumento. */
  double decode_block_double (char * bloco);
  /** M�todo usado para formatar um bloco a partir de um valor passado por argumento. */
  int forma_block_double (char ** res, int limite, char * lido);
  /** M�todo usado para formatar um bloco double aleatoriamente. */
  int forma_block_double_random(char ** res, int limite);

  /** M�todo que recupera o respectivo valor do bloco passado como argumento. */
  int decode_block_int (char * bloco);
  /** M�todo usado para formatar um bloco a partir de um valor passado por argumento. */
  int forma_block_int (char ** res, int limite, char * lido);
  /** M�todo usado para formatar um bloco aleatoriamente. */
  int forma_block_int_random(char ** res, int limite);

  /** M�todo usado para formatar um bloco string a partir de um valor passado por argumento. */
  int forma_block_string(char ** res, int limite, char * lido);
  /** M�todo usado para formatar um bloco string aleatoriamente. */
  int forma_block_string_random(char ** res, int limite);


public: // Public attributes
  /** Ponteiro para objeto tipo central, e serve para controlar a execu��o do framework. */
  central ** ctl;
  /** Atributo que contem a representa��o dos indiv�duos. */
  char * representacao;
};

#endif
