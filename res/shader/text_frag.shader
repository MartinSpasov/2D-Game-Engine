#version 430

in vec2 textureCoord;

out vec4 color;

uniform int letter;
uniform sampler2DArray diffuseTexture;
uniform vec4 textColor;

void main() {
	
	vec4 textureColor = texture(diffuseTexture, vec3(textureCoord.x, textureCoord.y, letter));
	
	if (textureColor.rgb == vec3(0.0,0.0,0.0)) {
		discard;
	}
	
	color = textureColor;
	//color = textColor;
}