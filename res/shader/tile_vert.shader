#version 430

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 inTextureCoord;
layout(location = 2) in int inIndex;
layout(location = 3) in mat4 modelMatrix;

out vec2 textureCoord;
flat out int index;

uniform mat4 vpMatrix;

void main() {
	gl_Position = vpMatrix * modelMatrix * position;
	textureCoord = inTextureCoord;
	index = inIndex;
}