#version 430

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 inTextureCoord;
layout(location = 2) in int inLetter;

uniform mat4 modelMatrix;
uniform float xOffset;

out vec2 textureCoord;

flat out int letter;

void main() {
	gl_Position = modelMatrix * position;
	gl_Position.x += xOffset * gl_InstanceID;
	textureCoord = inTextureCoord;
	letter = inLetter;
}