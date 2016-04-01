#version 430

in vec2 textureCoord;
flat in int letter;

out vec4 color;

//uniform int letter;
uniform sampler2DArray diffuseTexture;
uniform vec4 textColor;

void main() {
	
	float textureAlpha = texture(diffuseTexture, vec3(textureCoord.x, textureCoord.y, letter)).a;
	
	if (textureAlpha == 0) {
		discard;
	}
	
	//color = textureColor;
	color = vec4(textColor.rgb, textureAlpha);
}