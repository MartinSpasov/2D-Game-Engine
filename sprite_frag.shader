#version 430

in vec2 textureCoord;

out vec4 color;

uniform sampler2DArray diffuseTextureAtlas;

uniform int currentFrame;

void main() {
	//color = outColor;
	
	vec4 textureColor = texture(diffuseTextureAtlas, vec3(textureCoord.x, textureCoord.y, currentFrame));
	
	if (textureColor.a == 0) {
		discard;
	}
	
	color = textureColor;
}