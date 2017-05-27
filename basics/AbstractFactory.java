package basics;

/*
	PIZZA - has abstract prepare method
	      - is ABSTRACT CLASS that cannot be instantiated; expects OVERRIDDEN by SUBLASSES
	      - CAN offer partial TEMPLATE of overarching algo -- but INFLEXIBLE vs STRATEGY
	      - has specialized SUBCLASSES of CheesePizza, etc
	      - has CTOR DI argument for IngredientsFactory to INJECT custom STRATEGY to prepare Dough, Cheese etc.
	      **** prepare(IngredientFactoryStrategy)

	PIZZA STORE - has abstract method to allow CLIENT to pass in String Order of type of Pizza they want
	            - has specialized SUBCLASSES by Region of NY, CA, etc
	            - each has embedded attribute for CUSTOM-specialized Ingredients Factory
	            - KEY POINT:  this class PASSES IN its specialized-member Ingredients Factory STRATEGY implementation to
	                          CLIENT-SWITCHED CheesePizza selection
	                          to the prepare(...) interface which allows INPUT of STRATEGY
	                          to CREATE custom elements of a Cheese Pizza!
	            **** createPizza(String type of Pizza)
	                 - creates Pizza INSTANTIATED/passing in CUSTOM Ingredients Strategy set on CUSTOM-DI-CTOR-INJECITON
 */
/*
 * MAIN PRINCIPLES:
 * - avoid STATIC classes or method to be able to MOCK behavior out for testing, and to NOT hold global state
 * - http://stackoverflow.com/questions/4209791/design-patterns-abstract-factory-vs-factory-method
 * - Abstract Factory (Regional Ingredients)
 * Provide an interface for creating FAMILIES of related or dependent objects without specifying their concrete classes.
 * e.g. input is USER-DRIVEN STRING ID for class type
 * - Factory (can be EMBEDDED, COMPOSITE STRATEGY IMPLEMENTATION INSTANTIATION)
 * Define an interface for creating an object, but let the classes that implement the interface decide which class to instantiate.
 * The Factory method lets a class defer instantiation to subclasses.
 *
 * - decouple client-component via INTERFACES
 * - use ABSTRACT CLASSes for baseline TEMPLATE algorithms, with specialized parts left pure abstract
 * - use INHERITANCE DYNAMIC BINDing for SWITCH to specialized PARTs of SHARED template implementations
 * - use DEPENDENCY-INJECTED COMPOSITE for specialized STRATEGies for WHOLE algo specialized implementations
 * 
 * FACTORY:  
 * - DECOUPLEs object creation from usage by providing an INTERFACE with create() method
 * insulates client from changes in object type creation
 * - often used in TEMPLATE pattern where base class contains template functionality for say 
 * creating a basic pizza; BUT with ONE abstract method to be overridden by subclasses with custom implementation
 * e.g. createPizza for NYPizza and ChicagoPizza concrete subclass implementations
 * - must implement a concrete class on interface
 * - object type SWITCHing accomplished by INHERITANCE-DYNAMIC-BINDING to concrete subclass type
 * 
 * FACTORY METHOD:   (Create Pizza based on USER input String for Order)
 * - provides static method INTERFACE to create new objects
 * - can provide input variable that causes internal 'switch' for choice of different types of objects
 * - cannot be overridden in a subclass, as it's a static method
 * 
 * ABSTRACT FACTORY:  (Ingredients Factory is composite member of Store-specialization,
 *                     using CTOR-DI-injected specialized implementation of Factory method above!)
 * - ensures creation of RELATED objects
 * e.g. NYPizzaIngredients vs ChicagoPizzaIngredients
 * - provides interface of (multiple) factory methods for each object in set of related ones
 * - often used in a STRATEGY pattern, where it is embedded as a composite member of a driver class,
 * which uses its factory methods in a generic algo
 * e.g. Pizza is instantiated with ChicagoPizzaIngredients member, 
 * then its createPizza() calls into factory methods with a  Chicago-specific strategy
 *
 */

// Factory Pattern using TEMPLATE inheritance pattern for specialization
/*
public abstract class PizzaStore {
	// ... where type is Cheese, Clam, Veggie, etc
	protected abstract Pizza createPizza(String type);

}

public NYPizzaStore extends PizzaStore {
	
   // CUSTOM SUBCLASS INSTANTIATION  used for abstract factory LATER to CUSTOMIZE STRATEGY for type of pizza created via
   // TEMPLATE for Cheese pizza preparation!
   PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();

   // NOTE:  the following SWITCH is USER-driven; and NOT a PROPERTY of the Store Entity, so we ARE NOT using
   //        DYNAMIC-INHERITANCE-BINDING to accomplish switch for this case
   Pizza createPizza(String type) {
	   if (type.equals("cheese")) {
		   // we THUS we PASS-IN STRATEGY for NY pizza ingredients with TEMPLATE of preparing CheesePizzas
		   return new CheesePizza(ingredientFactory);
	   } else if { 
		   ...
	   }
   }
}

//- Product (Pizza) hierarchy MATCHes Factory hierarchy above 
public abstract class Pizza {
	
	// ...
	abstract void prepare();
}
*/

//- within Pizza itself, we have the use of
//  Abstract Factory Pattern using STRATEGY composition pattern 
//  with DEPENDENCY INJECTION via CTOR to allow for CUSTOMIZATION; and passed in from PARENT STORE based on USER-INPUT!
//  for specialization on ingredients FAMILY
/*
public class CheesePizza extends Pizza {
	
	PizzaIngredientFactory ingredientFactory;
	
	public CheesePizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}
	
	void prepare() {
		dough = ingredientFactory.createDough();
		sauce = ingredientFactory.createSauce();
		// ...
	}
}

public interface PizzaIngredientFactory {
	public Dough createDough()
	public Cheese createCheese();
	// ...
}

public class NYPizzaIngredientFactory {
	
	public Dough createDough() {
		return new ThinCrustDough();
	}
	public Cheese createCheese() {
		return new ReggianoCheese();
	} 	
	// ...
}
*/


