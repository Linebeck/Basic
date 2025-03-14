package com.linebeck.basic.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TextHandler {

	public static String getLegacyText(Component component) {
		String text = LegacyComponentSerializer.legacyAmpersand().serialize(component);
		return text;
	}

	public static String getPlainText(Component component) {
		String text = PlainTextComponentSerializer.plainText().serialize(component);
		return text;
	}

	public static TextComponent setText(String text, String hexCode) {
		TextComponent component = Component.text(text).color(TextColor.fromCSSHexString(hexCode)).decoration(TextDecoration.ITALIC, false);

		return component;
	}

	public static TextComponent setText(String text, String hexCode, String decoration) {
		TextComponent component = Component.text(text).color(TextColor.fromCSSHexString(hexCode)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.valueOf(decoration), true);
		return component;
	}

	public static TextComponent setText(String text, int r, int g, int b) {
		TextComponent component = Component.text(text).color(TextColor.color(r, g, b)).decoration(TextDecoration.ITALIC, false);
		return component;
	}

	public static TextComponent setText(String text, int r, int g, int b, String decoration) {
		TextComponent component = Component.text(text).color(TextColor.color(r, g, b)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.valueOf(decoration), true);
		return component;
	}

	public static TextComponent setText(String text, NamedTextColor namedTextColor) {
		TextComponent component = Component.text(text).color(namedTextColor);
		return component;
	}

	public static TextComponent setText(String text, NamedTextColor namedTextColor, String decoration) {
		TextComponent component = Component.text(text).color(namedTextColor).decoration(TextDecoration.valueOf(decoration), true);
		return component;
	}

	public static TextComponent setText(String text) {
		TextComponent component = Component.text(text);
		return component;
	}

	public static TextComponent setText(List<TextComponent> components) {
		TextComponent fullComponent = Component.empty();
		for(TextComponent component : components) { fullComponent = fullComponent.append(component); }
		return fullComponent;
	}

	public static TextComponent setText(LinkedHashMap<String, String> texts) {
		TextComponent fullComponent = Component.empty();
		for(Map.Entry<String, String> entry : texts.entrySet()) { fullComponent = fullComponent.append(setText(entry.getKey(), entry.getValue())); }
		return fullComponent;
	}

	public static List<Component> setComponents(Component component) {
		List<Component> components = new ArrayList<>();
		components.add(component);
		return components;
	}

	public static String showRawComponent(Component component) {
		String text = component.toString();
		return text;
	}

	public static String captilize(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}
}
