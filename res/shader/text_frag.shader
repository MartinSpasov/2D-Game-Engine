#version 430

in vec2 textureCoord;

out vec4 color;

uniform int letter;
uniform sampler2DArray diffuseTexture;

void main() {
	//color = vec4(1.0,0.0,0.0,1.0);
	
	vec4 textureColor = texture(diffuseTexture, vec3(textureCoord.x, textureCoord.y, letter));
	
	if (textureColor.a == 0) {
		discard;
	}
	
	color = textureColor;
}