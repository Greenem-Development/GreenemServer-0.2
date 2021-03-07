package com.comphenix.example;

import com.comphenix.example.Attributes.1;
import com.comphenix.example.Attributes.Attribute;
import com.comphenix.example.NbtFactory.NbtCompound;
import com.comphenix.example.NbtFactory.NbtList;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;

public class Attributes {
	public ItemStack stack;
	private NbtList attributes;

	public Attributes(ItemStack stack) {
		this.stack = NbtFactory.getCraftItemStack(stack);
		NbtCompound nbt = NbtFactory.fromItemTag(this.stack);
		this.attributes = nbt.getList("AttributeModifiers", true);
	}

	public ItemStack getStack() {
		return this.stack;
	}

	public int size() {
		return this.attributes.size();
	}

	public void add(Attribute attribute) {
		Preconditions.checkNotNull(attribute.getName(), "must specify an attribute name.");
		this.attributes.add(Attribute.access$000(attribute));
	}

	public boolean remove(Attribute attribute) {
		UUID uuid = attribute.getUUID();
		Iterator it = this.values().iterator();

		do {
			if (!it.hasNext()) {
				return false;
			}
		} while (!Objects.equal(((Attribute) it.next()).getUUID(), uuid));

		it.remove();
		return true;
	}

	public void clear() {
		this.attributes.clear();
	}

	public Attribute get(int index) {
      return new Attribute((NbtCompound)this.attributes.get(index), (1)null);
   }

	public Iterable<Attribute> values() {
      return () -> {
         return Iterators.transform(this.attributes.iterator(), (element) -> {
            return new Attribute((NbtCompound)element, (1)null);
         });
      };
   }
}