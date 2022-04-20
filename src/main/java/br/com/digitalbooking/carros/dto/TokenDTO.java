package br.com.digitalbooking.carros.dto;

public class TokenDTO {
    private String token;
    private String type;
    private UserDTO userDTO;

    public TokenDTO() {
    }

    public TokenDTO(String token, String type, UserDTO userDTO) {
        this.token = token;
        this.type = type;
        this.userDTO = userDTO.noPassword();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO.noPassword();
    }
}
