
package com.example.inventory.controller;

import com.example.inventory.model.InventoryItem;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/inventory")
public class InventoryController {
  @Autowired private InventoryRepository repo;

  @GetMapping public List<InventoryItem> listAll() { return repo.findAll(); }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public InventoryItem add(@RequestBody InventoryItem item) {
      return repo.save(item);
  }
  @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    repo.deleteById(id); return ResponseEntity.ok("Deleted");
  }
}