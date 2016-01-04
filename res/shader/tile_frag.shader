#version 430

in vec2 textureCoord;

out vec4 color;

uniform int index;
uniform sampler2DArray diffuseTexture;

void main() {
	vec4 textureColor = texture(diffuseTexture, vec3(textureCoord.x, textureCoord.y, index));
	
	if (textureColor.a == 0) {
		discard;
	}
	
	color = textureColor;
}