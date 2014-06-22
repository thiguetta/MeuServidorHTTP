/*
 * Copyright (C) 2014 Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 */
public class RespostaHTTP {

    private String protocolo;
    private int codigoResposta;
    private String mensagem;
    private byte[] conteudoResposta;
    private Map<String, List<String>> cabecalhos;
    private OutputStream saida;

    public RespostaHTTP() {

    }

    public RespostaHTTP(String protocolo, int codigoResposta, String mensagem) {
        this.protocolo = protocolo;
        this.codigoResposta = codigoResposta;
        this.mensagem = mensagem;
    }

    /**
     * Envia os dados da resposta ao cliente.
     *
     * @throws IOException
     */
    public void enviar() throws IOException {
        //escreve o headers em bytes
        saida.write(montaCabecalho());
        //escreve o conteudo em bytes
        saida.write(conteudoResposta);
        //encerra a resposta
        saida.flush();
    }

    /**
     * Insere um item de cabeçalho no mapa
     *
     * @param chave
     * @param valores lista com um ou mais valores para esta chave
     */
    public void setCabecalho(String chave, String... valores) {
        if (cabecalhos == null) {
            cabecalhos = new TreeMap<>();
        }
        cabecalhos.put(chave, Arrays.asList(valores));
    }

    /**
     * pega o tamanho da resposta em bytes
     *
     * @return retorna o valor em bytes do tamanho do conteudo da resposta
     * convertido em string
     */
    public String getTamanhoResposta() {
        return getConteudoResposta().length + "";
    }

    /**
     * converte o cabecalho em string.
     *
     * @return retorna o cabecalho em bytes
     */
    private byte[] montaCabecalho() {
        return this.toString().getBytes();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(protocolo).append(" ").append(codigoResposta).append(" ").append(mensagem).append("\r\n");
        for (Map.Entry<String, List<String>> entry : cabecalhos.entrySet()) {
            str.append(entry.getKey());
            String stringCorrigida = Arrays.toString(entry.getValue().toArray()).replace("[", "").replace("]", "");
            str.append(": ").append(stringCorrigida).append("\r\n");
        }
        str.append("\r\n");
        return str.toString();
    }

    //getters e setters
    public void setConteudoResposta(byte[] conteudoResposta) {
        this.conteudoResposta = conteudoResposta;
    }

    public void setSaida(OutputStream saida) {
        this.saida = saida;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public int getCodigoResposta() {
        return codigoResposta;
    }

    public void setCodigoResposta(int codigoResposta) {
        this.codigoResposta = codigoResposta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Map<String, List<String>> getCabecalhos() {
        return cabecalhos;
    }

    public void setCabecalhos(Map<String, List<String>> cabecalhos) {
        this.cabecalhos = cabecalhos;
    }

    public byte[] getConteudoResposta() {
        return conteudoResposta;
    }

    public OutputStream getSaida() {
        return saida;
    }

}
