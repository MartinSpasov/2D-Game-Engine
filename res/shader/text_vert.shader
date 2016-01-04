#version 430

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 inTextureCoord;

uniform mat4 modelMatrix;

out vec2 textureCoord;

void main() {
	gl_Position = modelMatrix * position;
	textureCoord = inTextureCoord;
}