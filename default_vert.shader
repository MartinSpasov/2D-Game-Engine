#version 430

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 inTextureCoord;

uniform mat4 mvpMatrix;

out vec2 textureCoord;

void main() {
	gl_Position = mvpMatrix * position;
	textureCoord = inTextureCoord;
}